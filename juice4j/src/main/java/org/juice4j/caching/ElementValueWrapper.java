package org.juice4j.caching;

import java.io.Serializable;

/**
 * Common interface providing wrapping behavior to an element value.
 * <p>
 * May be useful in use cases such as the following:<br>
 * It's desired to cache a given object X across different tiers of storage,
 * local heap included;<br>
 * X owns a reference to a given object Y;<br>
 * It's not desired to maintain such reference in cache;<br>
 * A solution portable to different cache providers is welcome.
 * <p>
 * We implement an <code>ElementValueWrapper</code> that nullifies reference to Y.<br>
 * Then we cache the wrapper instead of caching X directly.
 * <p>
 * Such implementation should state these side effects
 * related to the wrapping process.
 * @param <V> Element value type
 * @author Rafael Noronha
 */
public abstract class ElementValueWrapper<V> implements Serializable {
	private static final long serialVersionUID = -6650017684399761166L;

	private boolean alreadyWrapped;
	
	/**
	 * Wraps the given element value.
	 * @param value the value aimed to be wrapped
	 * @throws IllegalStateException when called more than once
	 */	
	public void wrap(V value) {
		if (this.alreadyWrapped) {
			throw new IllegalStateException("wrapping should happen only once");
		}
		
		this.doWrapping(value);
		this.alreadyWrapped = true;
	}
	
	/**
	 * Reconstructs the wrapped value.
	 * <p>
	 * Commonly used after bringing this wrapper instance from cache.
	 * @return the wrapped value. It's state should conform to side effects
	 * stated by the wrapper implementation contract.
	 * @throws IllegalStateException when called before a call to {@link #wrap(Object)}
	 */
	public V reconstruct() {
		if (!alreadyWrapped) {
			throw new IllegalStateException("reconstruction can't happen before wrapping");
		}
		
		return this.doReconstruct();
	}	
	
	/**
	 * Effectively wraps the element value. 
	 * Should conform to {@link #wrap(Object)} contract
	 */
	protected abstract void doWrapping(V value);
	
	/**
	 * Effectively reconstructs the element value. 
	 * Should conform to {@link #reconstruct()} contract
	 */		
	protected abstract V doReconstruct();
	
}
