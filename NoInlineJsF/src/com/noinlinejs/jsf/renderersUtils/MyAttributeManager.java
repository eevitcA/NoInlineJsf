package com.noinlinejs.jsf.renderersUtils;

import static com.sun.faces.renderkit.Attribute.attr;
import static com.sun.faces.util.CollectionsUtils.ar;

import java.util.Map;

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 * 
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 * 
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.util.CollectionsUtils;

/**
 * This class contains mappings between the standard components
 * and the passthrough attributes associated with them.
 */
public class MyAttributeManager {

    private static Map<String,Attribute[]> ATTRIBUTE_LOOKUP=CollectionsUtils.<String,Attribute[]>map()
        .add("CommandButton",ar(
            attr("accesskey")
            ,attr("alt")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("CommandLink",ar(
            attr("accesskey")
            ,attr("charset")
            ,attr("coords")
            ,attr("dir")
            ,attr("hreflang")
            ,attr("lang")
            ,attr("rel")
            ,attr("rev")
            ,attr("role")
            ,attr("shape")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
            ,attr("type")
        ))
        .add("DataTable",ar(
            attr("bgcolor")
            ,attr("border")
            ,attr("cellpadding")
            ,attr("cellspacing")
            ,attr("dir")
            ,attr("frame")
            ,attr("lang")
            ,attr("role")
            ,attr("rules")
            ,attr("style")
            ,attr("summary")
            ,attr("title")
            ,attr("width")
        ))
        .add("FormForm",ar(
            attr("accept")
            ,attr("dir")
            ,attr("enctype")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("target")
            ,attr("title")
        ))
        .add("GraphicImage",ar(
            attr("alt")
            ,attr("dir")
            ,attr("height")
            ,attr("lang")
            ,attr("longdesc")
            ,attr("role")
            ,attr("style")
            ,attr("title")
            ,attr("usemap")
            ,attr("width")
        ))
        .add("InputFile",ar(
            attr("accesskey")
            ,attr("alt")
            ,attr("dir")
            ,attr("lang")
            ,attr("maxlength")
            ,attr("role")
            ,attr("size")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("InputSecret",ar(
            attr("accesskey")
            ,attr("alt")
            ,attr("dir")
            ,attr("lang")
            ,attr("maxlength")
            ,attr("role")
            ,attr("size")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("InputText",ar(
            attr("accesskey")
            ,attr("alt")
            ,attr("dir")
            ,attr("lang")
            ,attr("maxlength")
            ,attr("role")
            ,attr("size")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("InputTextarea",ar(
            attr("accesskey")
            ,attr("cols")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("rows")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("MessageMessage",ar(
            attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("title")
        ))
        .add("MessagesMessages",ar(
            attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("title")
        ))
        .add("OutcomeTargetButton",ar(
            attr("accesskey")
            ,attr("alt")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("OutcomeTargetLink",ar(
            attr("accesskey")
            ,attr("charset")
            ,attr("coords")
            ,attr("dir")
            ,attr("hreflang")
            ,attr("lang")
            ,attr("rel")
            ,attr("rev")
            ,attr("role")
            ,attr("shape")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
            ,attr("type")
        ))
        .add("OutputFormat",ar(
            attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("title")
        ))
        .add("OutputLabel",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("OutputLink",ar(
            attr("accesskey")
            ,attr("charset")
            ,attr("coords")
            ,attr("dir")
            ,attr("hreflang")
            ,attr("lang")
            ,attr("rel")
            ,attr("rev")
            ,attr("role")
            ,attr("shape")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
            ,attr("type")
        ))
        .add("OutputText",ar(
            attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("title")
        ))
        .add("PanelGrid",ar(
            attr("bgcolor")
            ,attr("border")
            ,attr("cellpadding")
            ,attr("cellspacing")
            ,attr("dir")
            ,attr("frame")
            ,attr("lang")
            ,attr("role")
            ,attr("rules")
            ,attr("style")
            ,attr("summary")
            ,attr("title")
            ,attr("width")
        ))
        .add("PanelGroup",ar(
            attr("style")
        ))
        .add("SelectBooleanCheckbox",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectManyCheckbox",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectManyListbox",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectManyMenu",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectOneListbox",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectOneMenu",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("SelectOneRadio",ar(
            attr("accesskey")
            ,attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("tabindex")
            ,attr("title")
        ))
        .add("OutputBody",ar(
            attr("dir")
            ,attr("lang")
            ,attr("role")
            ,attr("style")
            ,attr("title")
            ,attr("xmlns")
        ))
        .add("OutputDoctype",ar(
            attr("public")
            ,attr("rootElement")
            ,attr("system")
        ))
        .add("OutputHead",ar(
            attr("dir")
            ,attr("lang")
            ,attr("xmlns")
        ))
        .fix();
    public enum Key {
        COMMANDBUTTON("CommandButton"),
        COMMANDLINK("CommandLink"),
        DATATABLE("DataTable"),
        FORMFORM("FormForm"),
        GRAPHICIMAGE("GraphicImage"),
        INPUTFILE("InputFile"),
        INPUTSECRET("InputSecret"),
        INPUTTEXT("InputText"),
        INPUTTEXTAREA("InputTextarea"),
        MESSAGEMESSAGE("MessageMessage"),
        MESSAGESMESSAGES("MessagesMessages"),
        OUTCOMETARGETBUTTON("OutcomeTargetButton"),
        OUTCOMETARGETLINK("OutcomeTargetLink"),
        OUTPUTFORMAT("OutputFormat"),
        OUTPUTLABEL("OutputLabel"),
        OUTPUTLINK("OutputLink"),
        OUTPUTTEXT("OutputText"),
        PANELGRID("PanelGrid"),
        PANELGROUP("PanelGroup"),
        SELECTBOOLEANCHECKBOX("SelectBooleanCheckbox"),
        SELECTMANYCHECKBOX("SelectManyCheckbox"),
        SELECTMANYLISTBOX("SelectManyListbox"),
        SELECTMANYMENU("SelectManyMenu"),
        SELECTONELISTBOX("SelectOneListbox"),
        SELECTONEMENU("SelectOneMenu"),
        SELECTONERADIO("SelectOneRadio"),
        OUTPUTBODY("OutputBody"),
        OUTPUTDOCTYPE("OutputDoctype"),
        OUTPUTHEAD("OutputHead");
        private String key;
        Key(String key) {
            this.key = key;
        }
        public String value() {
            return this.key;
        }
    }


    public static Attribute[] getAttributes(Key key) {
        return ATTRIBUTE_LOOKUP.get(key.value());
    }
}