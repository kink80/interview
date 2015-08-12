package com.blackboard.interview;

import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.*;

public class EntryTest {
	
	@DataProvider(name="validFileData")
	public Object[][] testData() {
		return new Object[][] {
			new Object[] {"name",        1},
			new Object[] {"české znaky", 3},
			new Object[] {"   name",     4},
			new Object[] {"name   ",     4},
			new Object[] {"  name  ",    5},
		};
	}
	
	@Test(dataProvider="validFileData")
	public void testValidEntries(String name, int size) {
		File fse = new File(name, size);
		
		assertEquals(fse.getName(), name.trim());
		assertEquals(fse.getSize(), size);
		
		Directory fse2 = new Directory();
		assertEquals(fse2.getName(), name.trim());
		assertEquals(fse2.getSize(), size);
	}
	
	@DataProvider(name="invalidFileData")
	public Object[][] testDataInvalid() {
		return new Object[][] {
			new Object[] {"  ",        1},
			new Object[] {",",         3},
			new Object[] {"\n",        4},
			new Object[] {"\t",        4},
			new Object[] {null,        5},
			new Object[] {null,       -5},
			new Object[] {"test",     -5},
			new Object[] {" test ",   -5},
			new Object[] {"test",      0},
			new Object[] {" test ",    0},
		};
	}
	
	@Test(dataProvider="invalidFileData")
	public void testInvalidEntries(String name, int size) {
		try {
			new File(name, size);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
		
		try {
			new Directory();
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
		
	}
	
	@Test
	public void testDirectory() {
		Directory root = new Directory();
		
		Directory kid1 = new Directory();
		kid1.addChild(new File("file1", 1));
		kid1.addChild(new File("file2", 2));
		assertEquals(kid1.getSize(), 3);
		
		root.addChild(kid1);
		assertEquals(root.getSize(), 3);
		
		Directory kid2 = new Directory();
		kid2.addChild(new File("file1", 4));
		kid2.addChild(new File("file2", 5));
		assertEquals(kid2.getSize(), 9);
		
		root.addChild(kid2);
		assertEquals(root.getSize(), 12);
	}

}
