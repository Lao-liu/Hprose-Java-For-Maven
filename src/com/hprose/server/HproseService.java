/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.net/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * HproseService.java                                     *
 *                                                        *
 * hprose service class for Java.                         *
 *                                                        *
 * LastModified: Dec 26, 2012                             *
 * Author: Ma Bingyao <andot@hprfc.com>                   *
 *                                                        *
\**********************************************************/
package com.hprose.server;

import com.hprose.common.HproseMethods;
import com.hprose.common.HproseMethod;
import com.hprose.common.HproseResultMode;
import com.hprose.common.HproseFilter;
import com.hprose.io.HproseMode;
import com.hprose.io.HproseTags;
import com.hprose.io.HproseReader;
import com.hprose.io.HproseWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class HproseService {

    private HproseMode mode = HproseMode.FieldMode;
    private boolean debugEnabled = false;
    private HproseServiceEvent event = null;
    protected HproseMethods globalMethods = null;
    private HproseFilter filter = null;

    public HproseMethods getGlobalMethods() {
        if (globalMethods == null) {
            globalMethods = new HproseMethods();
        }
        return globalMethods;
    }

    public void setGlobalMethods(HproseMethods methods) {
        this.globalMethods = methods;
    }

    public HproseMode getMode() {
        return mode;
    }

    public void setMode(HproseMode mode) {
        this.mode = mode;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }

    public HproseServiceEvent getEvent() {
        return this.event;
    }

    public void setEvent(HproseServiceEvent event) {
        this.event = event;
    }

    public HproseFilter getFilter() {
        return filter;
    }

    public void setFilter(HproseFilter filter) {
        this.filter = filter;
    }

    public void add(Method method, Object obj, String aliasName) {
        getGlobalMethods().addMethod(method, obj, aliasName);
    }

    public void add(Method method, Object obj, String aliasName, HproseResultMode mode) {
        getGlobalMethods().addMethod(method, obj, aliasName, mode);
    }

    public void add(String methodName, Object obj, Class<?>[] paramTypes, String aliasName) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, obj, paramTypes, aliasName);
    }

    public void add(String methodName, Object obj, Class<?>[] paramTypes, String aliasName, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, obj, paramTypes, aliasName, mode);
    }

    public void add(String methodName, Class<?> type, Class<?>[] paramTypes, String aliasName) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, type, paramTypes, aliasName);
    }

    public void add(String methodName, Class<?> type, Class<?>[] paramTypes, String aliasName, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, type, paramTypes, aliasName, mode);
    }

    public void add(String methodName, Object obj, Class<?>[] paramTypes) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, obj, paramTypes);
    }

    public void add(String methodName, Object obj, Class<?>[] paramTypes, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, obj, paramTypes, mode);
    }

    public void add(String methodName, Class<?> type, Class<?>[] paramTypes) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, type, paramTypes);
    }

    public void add(String methodName, Class<?> type, Class<?>[] paramTypes, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMethod(methodName, type, paramTypes, mode);
    }

    public void add(String methodName, Object obj, String aliasName) {
        getGlobalMethods().addMethod(methodName, obj, aliasName);
    }

    public void add(String methodName, Object obj, String aliasName, HproseResultMode mode) {
        getGlobalMethods().addMethod(methodName, obj, aliasName, mode);
    }

    public void add(String methodName, Class<?> type, String aliasName) {
        getGlobalMethods().addMethod(methodName, type, aliasName);
    }

    public void add(String methodName, Class<?> type, String aliasName, HproseResultMode mode) {
        getGlobalMethods().addMethod(methodName, type, aliasName, mode);
    }

    public void add(String methodName, Object obj) {
        getGlobalMethods().addMethod(methodName, obj);
    }

    public void add(String methodName, Object obj, HproseResultMode mode) {
        getGlobalMethods().addMethod(methodName, obj, mode);
    }

    public void add(String methodName, Class<?> type) {
        getGlobalMethods().addMethod(methodName, type);
    }

    public void add(String methodName, Class<?> type, HproseResultMode mode) {
        getGlobalMethods().addMethod(methodName, type, mode);
    }

    public void add(String[] methodNames, Object obj, String[] aliasNames) {
        getGlobalMethods().addMethods(methodNames, obj, aliasNames);
    }

    public void add(String[] methodNames, Object obj, String[] aliasNames, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, obj, aliasNames, mode);
    }

    public void add(String[] methodNames, Object obj, String aliasPrefix) {
        getGlobalMethods().addMethods(methodNames, obj, aliasPrefix);
    }

    public void add(String[] methodNames, Object obj, String aliasPrefix, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, obj, aliasPrefix, mode);
    }

    public void add(String[] methodNames, Object obj) {
        getGlobalMethods().addMethods(methodNames, obj);
    }

    public void add(String[] methodNames, Object obj, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, obj, mode);
    }

    public void add(String[] methodNames, Class<?> type, String[] aliasNames) {
        getGlobalMethods().addMethods(methodNames, type, aliasNames);
    }

    public void add(String[] methodNames, Class<?> type, String[] aliasNames, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, type, aliasNames, mode);
    }

    public void add(String[] methodNames, Class<?> type, String aliasPrefix) {
        getGlobalMethods().addMethods(methodNames, type, aliasPrefix);
    }

    public void add(String[] methodNames, Class<?> type, String aliasPrefix, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, type, aliasPrefix, mode);
    }

    public void add(String[] methodNames, Class<?> type) {
        getGlobalMethods().addMethods(methodNames, type);
    }

    public void add(String[] methodNames, Class<?> type, HproseResultMode mode) {
        getGlobalMethods().addMethods(methodNames, type, mode);
    }

    public void add(Object obj, Class<?> type, String aliasPrefix) {
        getGlobalMethods().addInstanceMethods(obj, type, aliasPrefix);
    }

    public void add(Object obj, Class<?> type, String aliasPrefix, HproseResultMode mode) {
        getGlobalMethods().addInstanceMethods(obj, type, aliasPrefix, mode);
    }

    public void add(Object obj, Class<?> type) {
        getGlobalMethods().addInstanceMethods(obj, type);
    }

    public void add(Object obj, Class<?> type, HproseResultMode mode) {
        getGlobalMethods().addInstanceMethods(obj, type, mode);
    }

    public void add(Object obj, String aliasPrefix) {
        getGlobalMethods().addInstanceMethods(obj, aliasPrefix);
    }

    public void add(Object obj, String aliasPrefix, HproseResultMode mode) {
        getGlobalMethods().addInstanceMethods(obj, aliasPrefix, mode);
    }

    public void add(Object obj) {
        getGlobalMethods().addInstanceMethods(obj);
    }

    public void add(Object obj, HproseResultMode mode) {
        getGlobalMethods().addInstanceMethods(obj, mode);
    }

    public void add(Class<?> type, String aliasPrefix) {
        getGlobalMethods().addStaticMethods(type, aliasPrefix);
    }

    public void add(Class<?> type, String aliasPrefix, HproseResultMode mode) {
        getGlobalMethods().addStaticMethods(type, aliasPrefix, mode);
    }

    public void add(Class<?> type) {
        getGlobalMethods().addStaticMethods(type);
    }

    public void add(Class<?> type, HproseResultMode mode) {
        getGlobalMethods().addStaticMethods(type, mode);
    }

    public void addMissingMethod(String methodName, Object obj) throws NoSuchMethodException {
        getGlobalMethods().addMissingMethod(methodName, obj);
    }

    public void addMissingMethod(String methodName, Object obj, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMissingMethod(methodName, obj, mode);
    }

    public void addMissingMethod(String methodName, Class<?> type) throws NoSuchMethodException {
        getGlobalMethods().addMissingMethod(methodName, type);
    }

    public void addMissingMethod(String methodName, Class<?> type, HproseResultMode mode) throws NoSuchMethodException {
        getGlobalMethods().addMissingMethod(methodName, type, mode);
    }

    protected abstract OutputStream getOutputStream() throws IOException;

    protected abstract InputStream getInputStream() throws IOException;

    protected void sendHeader() throws IOException {
        if (event != null) {
            event.onSendHeader();
        }
    }

    protected Object[] fixArguments(Type[] argumentTypes, Object[] arguments, int count) {
        return arguments;
    }

    protected void sendError(String error) throws IOException {
        if (event != null) {
            event.onSendError(error);
        }
        OutputStream ostream = getOutputStream();
        if (filter != null) {
            ostream = filter.outputFilter(ostream);
        }
        HproseWriter writer = new HproseWriter(ostream, mode);
        ostream.write(HproseTags.TagError);
        writer.writeString(error);
        ostream.write(HproseTags.TagEnd);
        ostream.flush();
    }

    protected void doInvoke(HproseMethods methods) throws Throwable {
        InputStream istream = getInputStream();
        if (filter != null) {
            istream = filter.inputFilter(istream);
        }
        HproseReader reader = new HproseReader(istream, mode);
        OutputStream ostream = getOutputStream();
        if (filter != null) {
            ostream = filter.outputFilter(ostream);
        }
        HproseWriter writer = new HproseWriter(ostream, mode);
        int tag;
        do {
            reader.reset();
            String name = reader.readString();
            String aliasname = name.toLowerCase();
            HproseMethod remoteMethod = null;
            int count = 0;
            Object[] args, arguments;
            boolean byRef = false;
            tag = reader.checkTags((char) HproseTags.TagList + "" +
                                   (char) HproseTags.TagEnd + "" +
                                   (char) HproseTags.TagCall);
            if (tag == HproseTags.TagList) {
                reader.reset();
                count = reader.readInt(HproseTags.TagOpenbrace);
                if (methods != null) {
                    remoteMethod = methods.get(aliasname, count);
                }
                if (remoteMethod == null) {
                    remoteMethod = getGlobalMethods().get(aliasname, count);
                }
                if (remoteMethod == null) {
                    arguments = reader.readArray(count);
                }
                else {
                    arguments = new Object[count];
                    reader.readArray(remoteMethod.paramTypes, arguments, count);
                }
                tag = reader.checkTags((char) HproseTags.TagTrue + "" +
                                       (char) HproseTags.TagEnd + "" +
                                       (char) HproseTags.TagCall);
                if (tag == HproseTags.TagTrue) {
                    byRef = true;
                    tag = reader.checkTags((char) HproseTags.TagEnd + "" +
                                           (char) HproseTags.TagCall);
                }
            }
            else {
                if (methods != null) {
                    remoteMethod = methods.get(aliasname, 0);
                }
                if (remoteMethod == null) {
                    remoteMethod = getGlobalMethods().get(aliasname, 0);
                }
                arguments = new Object[0];
            }
            if (event != null) {
                event.onBeforeInvoke(name, arguments, byRef);
            }
            if (remoteMethod == null) {
                args = arguments;
            }
            else {
                args = fixArguments(remoteMethod.paramTypes, arguments, count);
            }
            Object result;
            try {
                if (remoteMethod == null) {
                    if (methods != null) {
                        remoteMethod = methods.get("*", 2);
                    }
                    if (remoteMethod == null) {
                        remoteMethod = getGlobalMethods().get("*", 2);
                    }
                    if (remoteMethod == null) {
                        throw new NoSuchMethodError("Can't find this method " + name);
                    }
                    result = remoteMethod.method.invoke(remoteMethod.obj, new Object[]{name, args});
                }
                else {
                    result = remoteMethod.method.invoke(remoteMethod.obj, args);
                }
            }
            catch (ExceptionInInitializerError ex1) {
                Throwable e = ex1.getCause();
                if (e != null) {
                    throw e;
                }
                throw ex1;
            }
            catch (InvocationTargetException ex2) {
                Throwable e = ex2.getCause();
                if (e != null) {
                    throw e;
                }
                throw ex2;
            }
            if (byRef) {
                System.arraycopy(args, 0, arguments, 0, count);
            }
            if (event != null) {
                event.onAfterInvoke(name, arguments, byRef, result);
            }
            if (remoteMethod.mode == HproseResultMode.RawWithEndTag) {
                ostream.write((byte[])result);
                ostream.flush();
                return;
            }
            else if (remoteMethod.mode == HproseResultMode.Raw) {
                ostream.write((byte[])result);
            }
            else {
                ostream.write(HproseTags.TagResult);
                if (remoteMethod.mode == HproseResultMode.Serialized) {
                    ostream.write((byte[])result);
                }
                else {
                    writer.reset();
                    writer.serialize(result);
                }
                if (byRef) {
                    ostream.write(HproseTags.TagArgument);
                    writer.reset();
                    writer.writeArray(arguments);
                }
            }
        } while (tag == HproseTags.TagCall);
        ostream.write(HproseTags.TagEnd);
        ostream.flush();
    }

    protected void doFunctionList(HproseMethods methods) throws IOException {
        ArrayList<String> names = new ArrayList<String>();
        names.addAll(getGlobalMethods().getAllNames());
        if (methods != null) {
            names.addAll(methods.getAllNames());
        }
        OutputStream ostream = getOutputStream();
        if (filter != null) {
            ostream = filter.outputFilter(ostream);
        }
        HproseWriter writer = new HproseWriter(ostream, mode);
        ostream.write(HproseTags.TagFunctions);
        writer.writeList(names);
        ostream.write(HproseTags.TagEnd);
        ostream.flush();
    }

    protected void handle(HproseMethods methods) throws IOException {
        sendHeader();
        try {
            int tag = getInputStream().read();
            switch (tag) {
                case HproseTags.TagCall:
                    doInvoke(methods);
                    break;
                case HproseTags.TagEnd:
                    doFunctionList(methods);
                    break;
                default:
                    sendError("Unknown Tag");
                    break;
            }
        }
        catch (Throwable e) {
            if (debugEnabled) {
                StackTraceElement[] st = e.getStackTrace();
                StringBuffer es = new StringBuffer(e.toString()).append("\r\n");
                for (int i = 0, n = st.length; i < n; i++) {
                    es.append(st[i].toString()).append("\r\n");
                }
                sendError(es.toString());
            }
            else {
                sendError(e.toString());
            }
        }
    }
}