package org.juice4j.caching;

import java.util.Collection;

/**
 * Defines a common behavior to the generation of cache element key strings.
 * <p>
 * Concept's elucidation:<br>
 * Be K an element key represented by AA_BBBBB_C_DD static representation;<br>
 * AA, BBBBB, C and DD are static representations of K's components.
 * <p>
 * An <code>ElementKeyGenerator</code> implementation is responsible for generating a K value.<br>
 * An <code>ElementKeyComponentGenerator</code> implementation is responsible
 * for generating a value for a component of K.
 * @param <T> Type of the object expected to be parsed into a key string.
 * @author Rafael Noronha
 */
public abstract class ElementKeyGenerator<T> {
	private Collection<ElementKeyComponentGenerator<T>> cacheKeyComponents;
	
	/**
	 * Generates a string for this element key.
	 * @param obj Object that will be parsed into a string.
	 * @return The generated string.
	 * @throws IllegalStateException When components are null or empty.
	 */
	public String generate(T obj) {
		if (this.components() == null || this.components().isEmpty()) {
			throw new IllegalStateException("components incorretly initialized. should not be null or empty.");
		}
		
		StringBuilder sb = new StringBuilder();
		String value;
		for (ElementKeyComponentGenerator<T> c : this.components()) {
			value = c.generate(obj);
			if (value == null) {
				value = c.staticRepresentation();
			}
			
			sb.append(value);
			sb.append("_");
		}
		sb.delete(sb.length() - 1, sb.length());
		
		return sb.toString();
	}
	
	/**
	 * Initializes and returns a collection of {@link ElementKeyComponentGenerator} objects.
	 * <p>
	 * Key string generation will be possible through usage of this collection.
	 * @return The initialized collection.
	 */	
	protected abstract Collection<ElementKeyComponentGenerator<T>> initCacheKeyComponentGenerators();
	
	private  Collection<ElementKeyComponentGenerator<T>> components() {
		if (null == this.cacheKeyComponents) {
			this.cacheKeyComponents = this.initCacheKeyComponentGenerators();
		}
		
		return this.cacheKeyComponents;
	}	
	
}
