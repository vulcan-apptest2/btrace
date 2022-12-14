/*
 * Copyright (c) 2008, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the Classpath exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package traces.onmethod;

import static org.openjdk.btrace.core.BTraceUtils.*;

import java.util.ArrayList;
import java.util.List;
import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.Self;
import org.openjdk.btrace.core.types.AnyType;

/** @author Jaroslav Bachorik */
@BTrace
public class ArgsSigMatch {
  @OnMethod(clazz = "/.*\\.OnMethodTest/", method = "argsTypeMatch")
  public static void m1(@Self Object self, List<String> a) {
    println("m1");
  }

  @OnMethod(clazz = "/.*\\.OnMethodTest/", method = "argsTypeMatch", exactTypeMatch = true)
  public static void m2(@Self AnyType self, List<String> a) {
    println("m2");
  }

  @OnMethod(clazz = "/.*\\.OnMethodTest/", method = "argsTypeMatch", exactTypeMatch = true)
  public static void m3(@Self AnyType self, ArrayList<String> a) {
    println("m3");
  }
}
