package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import system.Content;

public class ContentTest {
  public Content testing;
  public Content testing1;
  public Content testing2;
  String expected;

  /**
   * Before each test, create new Content
   */
  @Before
  public void setUp() {
    testing = new Content("testing", "/utsc");
  }

  /**
   * Test Constructor
   */
  @Test
  public void testConstructor() {
    expected = "/utsc";
    
    // Checks for Content path
    assertEquals(expected, testing.getPath());
    
    expected = "testing";
    
    // Checks for Content name
    assertEquals(expected, testing.getName());

  }

  /**
   * Test setName
   */
  @Test
  public void testSetName() {
    expected = "NewName";
    
    // Set content name
    testing.setName("NewName");
    
    assertEquals(expected, testing.getName());
  }

  /**
   * Test setPath
   */
  @Test
  public void testSetPath() {
    expected = "/utsc";
    
    // Set content path
    testing.setPath("/utsc");
    
    assertEquals(expected, testing.getPath());
  }
  
  /**
   * Test setPath
   */
  @Test
  public void testClone() {
    expected = "/utsc";
    
    // Clone testing
    Content clone = testing.clone();
    
    assertEquals(expected, clone.getPath());
    
    expected = "testing";
    
    assertEquals(expected, clone.getName());
  }
}
