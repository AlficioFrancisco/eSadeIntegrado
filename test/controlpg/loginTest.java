/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import junit.framework.TestCase;

/**
 *
 * @author Paulino Francisco
 */
public class loginTest extends TestCase {
    
    public loginTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of doLogin method, of class login.
     */
    public void testDoLogin() throws Exception {
        System.out.println("doLogin");
        login instance = new login();
        instance.doLogin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
