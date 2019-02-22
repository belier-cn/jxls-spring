package cn.belier.jxls.cahce;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author belier
 * @date 2019/2/21
 */
@Slf4j
public class TemplateCache {

    /**
     * Default maximum number of entries for the view cache: 1024
     */
    public static final int DEFAULT_CACHE_LIMIT = 1024;


    /**
     * The maximum number of entries in the cache
     */
    @Getter
    @Setter
    private volatile int cacheLimit = DEFAULT_CACHE_LIMIT;

    @Getter
    @Setter
    private boolean cacheUnresolved = true;

    private static final byte[] EMPTY_TEMPLATE = new byte[0];

    /**
     * Fast access cache for Views, returning already cached instances without a global lock
     */
    private final Map<Object, byte[]> templateAccessCache = new ConcurrentHashMap<>(DEFAULT_CACHE_LIMIT);

    /**
     * Map from view key to View instance, synchronized for View creation
     */
    @SuppressWarnings("serial")
    private final Map<Object, byte[]> templateCreationCache =
            new LinkedHashMap<Object, byte[]>(DEFAULT_CACHE_LIMIT, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Object, byte[]> eldest) {
                    if (size() > getCacheLimit()) {
                        templateAccessCache.remove(eldest.getKey());
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    private PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();


    public boolean isCache() {
        return (this.cacheLimit > 0);
    }


    public byte[] getTemplate(String templatePath) {
        if (!isCache()) {
            return loadTemplate(templatePath);
        }

        byte[] template = this.templateAccessCache.get(templatePath);

        if (template == null) {

            synchronized (this.templateCreationCache) {

                template = this.templateCreationCache.get(templatePath);

                if (template == null) {
                    template = loadTemplate(templatePath);

                    if (template == null && this.cacheUnresolved) {
                        template = EMPTY_TEMPLATE;
                    }

                    if (template != null) {

                        this.templateAccessCache.put(templatePath, template);
                        this.templateCreationCache.put(templatePath, template);

                        if (log.isTraceEnabled()) {
                            log.trace("缓存模板 [" + templatePath + "]");
                        }
                    }

                }
            }
        }

        return (template != EMPTY_TEMPLATE ? template : null);
    }


    protected byte[] loadTemplate(String templatePath) {

        // 加载模板
        try {
            @Cleanup
            InputStream inputStream = templatePath.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)
                    ? this.patternResolver.getResource(templatePath).getInputStream()
                    : new FileInputStream(templatePath);

            if (inputStream != null) {

                return StreamUtils.copyToByteArray(inputStream);
            }

        } catch (IOException e) {

            if (log.isWarnEnabled()) {
                log.warn("找不到模板：" + templatePath, e);
            }
        }

        return null;

    }
}
