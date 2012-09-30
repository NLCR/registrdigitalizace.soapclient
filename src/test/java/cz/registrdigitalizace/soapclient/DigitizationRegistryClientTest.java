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
import cz.registrdigitalizace.soapservices.DigitizationState;
import cz.registrdigitalizace.soapservices.PlainQuery;
import cz.registrdigitalizace.soapservices.RecordFormat;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Functional tests.
 *
 * @author Jan Pokorsky
 */
public class DigitizationRegistryClientTest {

    private DigitizationRegistry registery;
    private static String url;

    public DigitizationRegistryClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        url = System.getProperty("cz.registrdigitalizace.soapclient.url");
        System.out.printf("url: '%s'\n", url);
        Assume.assumeNotNull(url);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws MalformedURLException {
        String user = System.getProperty("cz.registrdigitalizace.soapclient.user");
//        System.out.printf("user: '%s'\n", user);
        String passwd = System.getProperty("cz.registrdigitalizace.soapclient.passwd");
        DigitizationRegistryClient instance = new DigitizationRegistryClient(url, user, passwd);
        registery = instance.getRegistery();
        assertNotNull(registery);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testfindRecords() throws DigitizationRegistryException_Exception, UnsupportedEncodingException {
        PlainQuery query = new PlainQuery();
        query.setBarcode("26005405857");
//        query.setSignature("MIKROF 225");
        List<DigitizationRecord> records = registery.findRecords(query, RecordFormat.MARC_XML, null);
        assertNotNull(records);
        assertFalse(records.isEmpty());
        System.out.println("record.size: " + records.size());
        for (DigitizationRecord record : records) {
            System.out.println("recordId: " + record.getRecordId());
            System.out.println("recordState: " + record.getState());
            System.out.println("descriptor: " + dump(record.getDescriptor()));
        }
    }

    @Test
    public void testGetRecordState() throws DigitizationRegistryException_Exception, UnsupportedEncodingException, DatatypeConfigurationException {
        DigitizationState recordState = registery.getRecordState(45704);
        System.out.println("state: " + recordState);
        assertNotNull(recordState);
    }

    @Test(expected = DigitizationRegistryException_Exception.class)
    public void testSetRecordStateFail() throws DigitizationRegistryException_Exception, UnsupportedEncodingException, DatatypeConfigurationException {
        boolean result = registery.setRecordState(45704, null, null, null, null);
        System.out.println("result: " + result);
    }

    private static String dump(Source src) {
        if (src == null) {
            return null;
        }
        try {
            TransformerFactory fact = TransformerFactory.newInstance();
            Transformer t = fact.newTransformer();
            StringWriter dump = new StringWriter();
            t.transform(src, new StreamResult(dump));
            return dump.toString();
        } catch (TransformerException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
