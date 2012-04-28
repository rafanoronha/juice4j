package org.juice4j.caching;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Rafael Noronha
 */
public class ElementValueWrapperTest {
	
	@Test
	public void should_wrap() {
		new DummyCachedObjectWrapper().wrap(new CachedObject());
	}
	
	@Test
	public void should_return_reconstructed_object() {
		// ARRANGE
		CachedObject wrapped = new CachedObject();
		DummyCachedObjectWrapper wrapper = new DummyCachedObjectWrapper();
		wrapper.wrap(wrapped);
		
		// ACT
		CachedObject reconstructed = wrapper.reconstruct();
		
		// ASSERT
		assertEquals(wrapped, reconstructed);
	}
	
	@Test(expected=IllegalStateException.class)
	public void wrapping_must_be_done_only_once() {
		// ARRANGE
		CachedObject wrapped = new CachedObject();
		DummyCachedObjectWrapper wrapper = new DummyCachedObjectWrapper();
		
		// ACT
		wrapper.wrap(wrapped);
		wrapper.wrap(wrapped);
	}
	
	@Test(expected=IllegalStateException.class)
	public void cant_reconstruct_before_wrapping() {
		new DummyCachedObjectWrapper().reconstruct();
	}	
	
	class CachedObject {
	}
	
	@SuppressWarnings("serial")
	class DummyCachedObjectWrapper extends ElementValueWrapper<CachedObject> {
		private CachedObject wrapped;
		
		@Override
		protected void doWrapping(CachedObject value) {
			this.wrapped = value;
		}

		@Override
		protected CachedObject doReconstruct() {
			return this.wrapped;
		}
	}
	
}
