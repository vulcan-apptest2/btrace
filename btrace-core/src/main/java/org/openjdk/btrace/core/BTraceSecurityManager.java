package org.openjdk.btrace.core;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicBoolean;

public class BTraceSecurityManager extends SecurityManager {
    private final SecurityManager parent;
    private final DebugSupport debug;

    private final AtomicBoolean installed = new AtomicBoolean(true);
    public BTraceSecurityManager(SecurityManager parent, DebugSupport debug) {
        this.parent = parent;
        this.debug = debug;
    }

    @Override
    public void checkPermission(Permission perm) {
        SecurityException ex = null;
        try {
            if (parent != null) {
                parent.checkPermission(perm);
            }
        } catch (SecurityException e) {
            ex = e;
        }
        if (ex != null && isUnsafeThread()) {
            throw ex;
        }
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        SecurityException ex = null;
        try {
            if (parent != null) {
                parent.checkPermission(perm, context);
            }
        } catch (SecurityException e) {
            ex = e;
        }
        if (ex != null && isUnsafeThread()) {
            throw ex;
        }
    }

    public static BTraceSecurityManager install(DebugSupport debug) {
        return AccessController.doPrivileged((PrivilegedAction<BTraceSecurityManager>) () -> {
            SecurityManager parent = System.getSecurityManager();
            BTraceSecurityManager sm = new BTraceSecurityManager(parent, debug);
            System.setSecurityManager(sm);
            return sm;
        });
    }

    public void uninstall() {
        if (installed.compareAndSet(true, false)) {
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                System.setSecurityManager(parent);
                return null;
            });
        }
    }

    private boolean isUnsafeThread() {
        Thread thrd = Thread.currentThread();
        if (thrd.getContextClassLoader() == null) {
            String threadName = Thread.currentThread().getName();
            return !threadName.equals("Attach Listener") && !threadName.contains("BTrace");
        }
        return true;
    }
}
