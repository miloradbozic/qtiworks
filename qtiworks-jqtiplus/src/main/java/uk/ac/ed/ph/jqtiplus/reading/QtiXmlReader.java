/* Copyright (c) 2012, University of Edinburgh.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of the University of Edinburgh nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * This software is derived from (and contains code from) QTItools and MathAssessEngine.
 * QTItools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package uk.ac.ed.ph.jqtiplus.reading;

import uk.ac.ed.ph.jqtiplus.ExtensionNamespaceInfo;
import uk.ac.ed.ph.jqtiplus.JqtiExtensionManager;
import uk.ac.ed.ph.jqtiplus.QtiConstants;
import uk.ac.ed.ph.jqtiplus.xmlutils.ResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlReadResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceNotFoundException;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceReader;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceReaderException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.validation.Schema;

/**
 * Wraps around {@link XmlResourceReader} to provide specific support for QTI 2.1
 * and optional extensions.
 * 
 * @author David McKain
 */
public final class QtiXmlReader {

    private final JqtiExtensionManager jqtiExtensionManager;
    
    /** Delegating {@link XmlResourceReader} */
    private final XmlResourceReader xmlResourceReader;
    
    public QtiXmlReader() {
        this(null, null, null);
    }
    
    public QtiXmlReader(JqtiExtensionManager jqtiExtensionManager) {
        this(jqtiExtensionManager, null, null);
    }
    
    public QtiXmlReader(JqtiExtensionManager jqtiExtensionManager, ResourceLocator parserResourceLocator) {
        this(jqtiExtensionManager, parserResourceLocator, null);
    }
    
    public QtiXmlReader(ResourceLocator parserResourceLocator) {
        this(null, parserResourceLocator, null);
    }
    
    public QtiXmlReader(JqtiExtensionManager jqtiExtensionManager, Map<String, Schema> schemaCacheMap) {
        this(jqtiExtensionManager, null, schemaCacheMap);
    }

    public QtiXmlReader(JqtiExtensionManager jqtiExtensionManager, ResourceLocator parserResourceLocator,
            Map<String, Schema> schemaCacheMap) {
        /* Merge extension schema with QTI 2.1 schema */
        final Map<String, String> resultingSchemaMapTemplate = new HashMap<String, String>();
        if (jqtiExtensionManager!=null) {
            for (Entry<String, ExtensionNamespaceInfo> entry : jqtiExtensionManager.getExtensionNamepaceInfoMap().entrySet()) {
                resultingSchemaMapTemplate.put(entry.getKey(), entry.getValue().getSchemaUri());
            }
        }
        resultingSchemaMapTemplate.put(QtiConstants.QTI_21_NAMESPACE_URI, QtiConstants.QTI_21_SCHEMA_LOCATION);

        this.jqtiExtensionManager = jqtiExtensionManager;
        this.xmlResourceReader = new XmlResourceReader(parserResourceLocator, resultingSchemaMapTemplate, schemaCacheMap);
    }
    
    public JqtiExtensionManager getJqtiExtensionManager() {
        return jqtiExtensionManager;
    }

    public ResourceLocator getParserResourceLocator() {
        return xmlResourceReader.getParserResourceLocator();
    }

    public Map<String, Schema> getSchemaCacheMap() {
        return xmlResourceReader.getSchemaCacheMap();
    }

    //--------------------------------------------------

    /**
     * @throws XmlResourceNotFoundException if the XML resource with the given System ID cannot be
     *             located using the given {@link ResourceLocator}
     * @throws XmlResourceReaderException if an unexpected Exception occurred parsing and/or validating the XML, or
     *             if any of the required schemas could not be located.
     */
    public XmlReadResult read(URI systemIdUri, ResourceLocator inputResourceLocator, boolean schemaValidating)
            throws XmlResourceNotFoundException {
        return xmlResourceReader.read(systemIdUri, inputResourceLocator, schemaValidating);
    }
    
    /**
     * Creates a new {@link QtiXmlObjectReader} from this reader and the given 
     * input {@link ResourceLocator}.
     */
    public QtiXmlObjectReader createQtiXmlObjectReader(ResourceLocator inputResourceLocator) {
        return new QtiXmlObjectReader(this, inputResourceLocator);
    }

    //--------------------------------------------------

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode()
                + "(parserResourceLocator=" + getParserResourceLocator()
                + ",jqtiExtensionManager=" + jqtiExtensionManager
                + ",schemaCacheMap=" + getSchemaCacheMap()
                + ")";
    }
}