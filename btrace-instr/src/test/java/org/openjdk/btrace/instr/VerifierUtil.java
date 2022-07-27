package org.openjdk.btrace.instr;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileInputStream;
import java.io.PrintWriter;

public class VerifierUtil {
  private static final String classPath = "/tmp/btrace_3/sun/font/CStrike$GlyphInfoCache.class";
  @Test
  void verifyClass() throws Exception {
    org.objectweb.asm.util.CheckClassAdapter.verify(new ClassReader(new FileInputStream(classPath)), true, new PrintWriter(System.out));
  }

  @Test
  void printClass() throws Exception {
    new ClassReader(new FileInputStream(classPath)).accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
  }
}
