/*
 * Copyright (C) 2012 Jan Pokorsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.registrdigitalizace.soapclient;

import cz.registrdigitalizace.soapservices.DigitizationRecord;
import cz.registrdigitalizace.soapservices.DigitizationRegistry;
import cz.registrdigitalizace.soapservices.DigitizationRegistryException_Exception;
import cz.registrdigitalizace.soapservices.PlainQuery;
import cz.registrdigitalizace.soapservices.RecordFormat;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TODO: link URL, user and passwd with POM and system properties
 *
 * @author Jan Pokorsky
 */
@Ignore
public class DigitizationRegistryClientTest {

    private DigitizationRegistry registery;
    private String url;

    public DigitizationRegistryClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MalformedURLException {
        url = null;
        String user = "XXX";
        String passwd = "XXX";
        DigitizationRegistryClient instance = new DigitizationRegistryClient(url, user, passwd);
        registery = instance.getRegistery();
        assertNotNull(registery);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getRegistery method, of class DigitizationRegistryClient.
     */
    @Test
    public void testGetRegistery() throws DigitizationRegistryException_Exception, UnsupportedEncodingException {
        System.out.println("getRegistery");
        PlainQuery query = new PlainQuery();
//        query.setBarcode("1001742037");
        query.setBarcode("26005405857");
        // TODO review the generated test code and remove the default call to fail.
        //        fail("The test case is a prototype.");
        List<DigitizationRecord> records = registery.findRecords(query, RecordFormat.MARC_XML);
        assertNotNull(records);
        assertFalse(records.isEmpty());
        System.out.println("record.size: " + records.size());
        for (DigitizationRecord record : records) {
            System.out.println("recordId: " + record.getRecordId());
            System.out.println("recordState: " + record.getState());
            String desc = record.getDescriptor() == null
                    ? "null" : new String(record.getDescriptor(), "UTF-8");
            System.out.println("recordState: " + desc);
        }
    }
}
