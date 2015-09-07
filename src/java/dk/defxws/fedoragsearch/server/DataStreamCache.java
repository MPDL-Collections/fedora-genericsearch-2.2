/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE
 * or http://www.escidoc.de/license.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at license/ESCIDOC.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

package dk.defxws.fedoragsearch.server;


import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Gudrun Siedersleben
 *         Singleton for caching  extracted full texts
 */
public final class DataStreamCache {

    private static final int LIVE_TIME = 360;
	private static final int IDLE_TIME = 360;

	private static final int INDEXER_CACHE_SIZE = 40;

    private final Cache dataStreamCache;
    private static final Logger logger = LoggerFactory.getLogger(DataStreamCache.class);

    /**
     * private Constructor for Singleton.
     */
    private DataStreamCache() {

        final CacheManager cacheManager = CacheManager.create();
        this.dataStreamCache = new Cache(
        		new CacheConfiguration("dataStreamCache", INDEXER_CACHE_SIZE).timeToIdleSeconds(IDLE_TIME).timeToLiveSeconds(LIVE_TIME));
        cacheManager.addCache(this.dataStreamCache);
    }

    public static DataStreamCache getInstance() {
        return DataStreamCacheHolder.instance;
    }
    
	public boolean containsKey(String pid, String dsId, String threadId) {
		return this.dataStreamCache.get(new Tripel( pid, dsId, threadId)) != null;
	}
	
	public String get(String pid, String dsId, String threadId) {
		Element e = this.dataStreamCache.get(new Tripel(pid, dsId, threadId));
		
		if (e == null) {
			logger.info("No element foud for "
					+ (new Tripel(pid, dsId, threadId)).toString());
			return "";
		}
		return (String) e.getObjectValue();
	}
	
	public void put(String pid, String dsId, String threadId, String string) {
		this.dataStreamCache.put(new Element(new Tripel(pid, dsId, threadId), string));	
	}

	public int size() {
		return this.dataStreamCache.getSize();
	}
	
	private static class DataStreamCacheHolder {
		private static final DataStreamCache instance = new DataStreamCache();
	}

    private class Tripel implements Serializable {
    	
		private static final long serialVersionUID = 1L;
		
		private String pid;
    	private String dataStreamId;
    	private String threadId;
    	
    	Tripel(final String p, final String ds, final String t) {
    		this.pid = p;
    		this.dataStreamId = ds; 	
    		this.threadId = t;
    	}
    	
    	@Override
    	public int hashCode()
    	{
    		return pid.hashCode() + dataStreamId.hashCode() + threadId.hashCode();
    	}
    	
    	@Override
    	public boolean equals(Object obj) {
    		if (!(obj instanceof Tripel)) {
    			return false;
    		}
    		if (obj == this) {
                return true;
            }
			if (obj == null || obj.getClass() != this.getClass())
				return false;

			Tripel other = (Tripel) obj;
			boolean b = pid.equals(other.pid) 
								&& dataStreamId.equals(other.dataStreamId) && threadId.equals(other.threadId);	
			return b;
    	}
    	
    	public String toString() {
    		return "(" + pid + ", " + dataStreamId + ", " + threadId + ")"; 
    	}
    }
}