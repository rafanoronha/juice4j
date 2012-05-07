package org.juice4j.caching;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

/**
 * @author Rafael Noronha
 */
public class ElementKeyGeneratorTest {
	
	@Test
	public void should_join_component_values() {
		// ARRANGE
		ParsedObject obj = new ParsedObject();
		GeneratorStub generator = new GeneratorStub();
		String expected = "C5_00892_1_85";
		
		// ACT
		String actual = generator.generate(obj);
		
		// ASSERT
		assertEquals(expected, actual);		
	}
	
	@Test
	public void should_fallback_component_null_values_to_static_representations() {
		// ARRANGE
		ParsedObject obj = null;
		GeneratorStub generator = new GeneratorStub();
		String expected = "AA_BBBBB_C_DD";
		
		// ACT
		String actual = generator.generate(obj);
		
		// ASSERT
		assertEquals(expected, actual);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_handle_null_component_collection() {
		// ARRANGE
		ParsedObject obj = new ParsedObject();
		
		ElementKeyGenerator<ParsedObject> generator = new
				ElementKeyGenerator<ElementKeyGeneratorTest.ParsedObject>() {
					@Override
					protected Collection<ElementKeyComponentGenerator<ParsedObject>> initCacheKeyComponentGenerators() {
						return null;
					}
				};
		
		// ACT
		generator.generate(obj);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_handle_empty_component_collection() {
		// ARRANGE
		ParsedObject obj = new ParsedObject();
		
		ElementKeyGenerator<ParsedObject> generator = new
				ElementKeyGenerator<ElementKeyGeneratorTest.ParsedObject>() {
					@Override
					protected Collection<ElementKeyComponentGenerator<ParsedObject>> initCacheKeyComponentGenerators() {
						return new ArrayList<ElementKeyComponentGenerator<ParsedObject>>();
					}
				};
		
		// ACT
		generator.generate(obj);
	}
	
	class ParsedObject {
	}
	
	class GeneratorStub extends ElementKeyGenerator<ParsedObject> {
		@Override
		protected Collection<ElementKeyComponentGenerator<ParsedObject>> initCacheKeyComponentGenerators() {
			Collection<ElementKeyComponentGenerator<ParsedObject>> components =
					new ArrayList<ElementKeyComponentGenerator<ParsedObject>>();			
			
			components.add(new ElementKeyComponentGenerator<ParsedObject>() {
				public String staticRepresentation() {
					return "AA";
				}
				public String generate(ParsedObject o) {
					String value;
					if (o != null) {
						value = "C5";
					} else {
						value = this.staticRepresentation();
					}
					return value;					
				}
			});
			components.add(new ElementKeyComponentGenerator<ParsedObject>() {
				public String staticRepresentation() {
					return "BBBBB";
				}
				public String generate(ParsedObject o) {
					String value = null;
					if (o != null) {
						value = "00892";
					}
					return value;					
				}
			});
			components.add(new ElementKeyComponentGenerator<ParsedObject>() {
				public String staticRepresentation() {
					return "C";
				}
				public String generate(ParsedObject o) {
					String value = null;
					if (o != null) {
						value = "1";
					}
					return value;
				}
			});
			components.add(new ElementKeyComponentGenerator<ParsedObject>() {
				public String staticRepresentation() {
					return "DD";
				}
				public String generate(ParsedObject o) {
					String value = null;
					if (o != null) {
						value = "85";
					}
					return value;
				}
			});			
			
			return components;
		}
	}
	
}
