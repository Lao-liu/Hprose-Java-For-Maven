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
 * HproseClient.java                                      *
 *                                                        *
 * hprose client class for Java.                          *
 *                                                        *
 * LastModified: Dec 26, 2012                             *
 * Author: Ma Bingyao <andot@hprfc.com>                   *
 *                                                        *
\**********************************************************/
package com.hprose.client;

import com.hprose.common.HproseInvocationHandler;
import com.hprose.common.HproseErrorEvent;
import com.hprose.common.HproseCallback;
import com.hprose.common.HproseInvoker;
import com.hprose.common.HproseException;
import com.hprose.common.HproseResultMode;
import com.hprose.common.HproseFilter;
import com.hprose.io.HproseMode;
import com.hprose.io.HproseWriter;
import com.hprose.io.HproseReader;
import com.hprose.io.HproseTags;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public abstract class HproseClient implements HproseInvoker {

    private static final Object[] nullArgs = new Object[0];
    private HproseMode mode;
    private HproseFilter filter = null;
    public HproseErrorEvent onError = null;

    protected HproseClient() {
        this.mode = HproseMode.FieldMode;
    }

    protected HproseClient(String uri) {
        useService(uri);
        this.mode = HproseMode.FieldMode;
    }

    protected HproseClient(HproseMode mode) {
        this.mode = mode;
    }

    protected HproseClient(String uri, HproseMode mode) {
        useService(uri);
        this.mode = mode;
    }

    public HproseFilter getFilter() {
        return filter;
    }

    public void setFilter(HproseFilter filter) {
        this.filter = filter;
    }

    public abstract void useService(String uri);

    public final Object useService(Class<?> type) {
        return useService(type, null);
    }

    public final Object useService(String uri, Class<?> type) {
        return useService(uri, type, null);
    }

    public final Object useService(Class<?>[] types) {
        return useService(types, null);
    }

    public final Object useService(String uri, Class<?>[] types) {
        return useService(uri, types, null);
    }

    public final Object useService(Class<?> type, String ns) {
        HproseInvocationHandler handler = new HproseInvocationHandler(this, ns);
        if (type.isInterface()) {
            return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
        }
        else {
            return Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), handler);
        }
    }
    
    public final Object useService(String uri, Class<?> type, String ns) {
        useService(uri);
        return useService(type, ns);
    }
    
    public final Object useService(Class<?>[] types, String ns) {
        HproseInvocationHandler handler = new HproseInvocationHandler(this, ns);
        return Proxy.newProxyInstance(types[0].getClassLoader(), types, handler);
    }
    
    public final Object useService(String uri, Class<?>[] types, String ns) {
        useService(uri);
        return useService(types, ns);
    }

    public final void invoke(String functionName, HproseCallback<?> callback) {
        invoke(functionName, nullArgs, callback, null, null, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, HproseCallback<?> callback, HproseErrorEvent errorEvent) {
        invoke(functionName, nullArgs, callback, errorEvent, null, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback) {
        invoke(functionName, arguments, callback, null, null, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent) {
        invoke(functionName, arguments, callback, errorEvent, null, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, boolean byRef) {
        invoke(functionName, arguments, callback, null, null, byRef, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent, boolean byRef) {
        invoke(functionName, arguments, callback, errorEvent, null, byRef, HproseResultMode.Normal);
    }

    public final void invoke(String functionName, HproseCallback<?> callback, Type returnType) {
        invoke(functionName, nullArgs, callback, null, returnType, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, HproseCallback<?> callback, HproseErrorEvent errorEvent, Type returnType) {
        invoke(functionName, nullArgs, callback, errorEvent, returnType, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, Type returnType) {
        invoke(functionName, arguments, callback, null, returnType, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent, Type returnType) {
        invoke(functionName, arguments, callback, errorEvent, returnType, false, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, Type returnType, boolean byRef) {
        invoke(functionName, arguments, callback, null, returnType, byRef, HproseResultMode.Normal);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent, Type returnType, boolean byRef){
        invoke(functionName, arguments, callback, errorEvent, returnType, byRef, HproseResultMode.Normal);
    }

    public final <T> void invoke(String functionName, HproseCallback<T> callback, Class<T> returnType) {
        invoke(functionName, nullArgs, callback, null, returnType, false, HproseResultMode.Normal);
    }
    public final <T> void invoke(String functionName, HproseCallback<T> callback, HproseErrorEvent errorEvent, Class<T> returnType) {
        invoke(functionName, nullArgs, callback, errorEvent, returnType, false, HproseResultMode.Normal);
    }
    public final <T> void invoke(String functionName, Object[] arguments, HproseCallback<T> callback, Class<T> returnType) {
        invoke(functionName, arguments, callback, null, returnType, false, HproseResultMode.Normal);
    }
    public final <T> void invoke(String functionName, Object[] arguments, HproseCallback<T> callback, HproseErrorEvent errorEvent, Class<T> returnType) {
        invoke(functionName, arguments, callback, errorEvent, returnType, false, HproseResultMode.Normal);
    }
    public final <T> void invoke(String functionName, Object[] arguments, HproseCallback<T> callback, Class<T> returnType, boolean byRef) {
        invoke(functionName, arguments, callback, null, returnType, byRef, HproseResultMode.Normal);
    }
    public final <T> void invoke(String functionName, Object[] arguments, HproseCallback<T> callback, HproseErrorEvent errorEvent, Class<T> returnType, boolean byRef) {
        invoke(functionName, arguments, callback, errorEvent, returnType, byRef, HproseResultMode.Normal);
    }

    public final void invoke(String functionName, HproseCallback<?> callback, HproseResultMode resultMode) {
        invoke(functionName, nullArgs, callback, null, null, false, resultMode);
    }
    public final void invoke(String functionName, HproseCallback<?> callback, HproseErrorEvent errorEvent, HproseResultMode resultMode) {
        invoke(functionName, nullArgs, callback, errorEvent, null, false, resultMode);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseResultMode resultMode) {
        invoke(functionName, arguments, callback, null, null, false, resultMode);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent, HproseResultMode resultMode) {
        invoke(functionName, arguments, callback, errorEvent, null, false, resultMode);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, boolean byRef, HproseResultMode resultMode) {
        invoke(functionName, arguments, callback, null, null, byRef, resultMode);
    }
    public final void invoke(String functionName, Object[] arguments, HproseCallback<?> callback, HproseErrorEvent errorEvent, boolean byRef, HproseResultMode resultMode) {
        invoke(functionName, arguments, callback, errorEvent, null, byRef, resultMode);
    }

    @SuppressWarnings("unchecked")
    private void invoke(final String functionName, final Object[] arguments, final HproseCallback callback, final HproseErrorEvent errorEvent, final Type returnType, final boolean byRef, final HproseResultMode resultMode) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Object result = invoke(functionName, arguments, returnType, byRef, resultMode);
                    callback.handler(result, arguments);
                }
                catch (Throwable ex) {
                    if (errorEvent != null) {
                        errorEvent.handler(functionName, ex);
                    }
                    else if (onError != null) {
                        onError.handler(functionName, ex);
                    }
                }
            }
        }.start();
    }

    public final Object invoke(String functionName) throws IOException {
        return invoke(functionName, nullArgs, (Class<?>)null, false, HproseResultMode.Normal);
    }
    public final Object invoke(String functionName, Object[] arguments) throws IOException {
        return invoke(functionName, arguments, (Class<?>)null, false, HproseResultMode.Normal);
    }
    public final Object invoke(String functionName, Object[] arguments, boolean byRef) throws IOException {
        return invoke(functionName, arguments, (Class<?>)null, byRef, HproseResultMode.Normal);
    }

    public final Object invoke(String functionName, Type returnType) throws IOException {
        return invoke(functionName, nullArgs, returnType, false, HproseResultMode.Normal);
    }
    public final Object invoke(String functionName, Object[] arguments, Type returnType) throws IOException {
        return invoke(functionName, arguments, returnType, false, HproseResultMode.Normal);
    }
    public final Object invoke(String functionName, Object[] arguments, Type returnType, boolean byRef) throws IOException {
        return invoke(functionName, arguments, returnType, byRef, HproseResultMode.Normal);
    }

    @SuppressWarnings("unchecked")
    public final <T> T invoke(String functionName, Class<T> returnType) throws IOException {
        return (T) invoke(functionName, nullArgs, returnType, false, HproseResultMode.Normal);
    }
    @SuppressWarnings("unchecked")
    public final <T> T invoke(String functionName, Object[] arguments, Class<T> returnType) throws IOException {
        return (T) invoke(functionName, arguments, returnType, false, HproseResultMode.Normal);
    }
    @SuppressWarnings("unchecked")
    public final <T> T invoke(String functionName, Object[] arguments, Class<T> returnType, boolean byRef) throws IOException {
        return (T) invoke(functionName, arguments, returnType, byRef, HproseResultMode.Normal);
    }

    public final Object invoke(String functionName, HproseResultMode resultMode) throws IOException {
        return invoke(functionName, nullArgs, (Class<?>)null, false, resultMode);
    }
    public final Object invoke(String functionName, Object[] arguments, HproseResultMode resultMode) throws IOException {
        return invoke(functionName, arguments, (Class<?>)null, false, resultMode);
    }
    public final Object invoke(String functionName, Object[] arguments, boolean byRef, HproseResultMode resultMode) throws IOException {
        return invoke(functionName, arguments, (Class<?>)null, byRef, resultMode);
    }

    private Object invoke(String functionName, Object[] arguments, Type returnType, boolean byRef, HproseResultMode resultMode) throws IOException {
        Object context = getInvokeContext();
        OutputStream ostream = getOutputStream(context);
        boolean success = false;
        try {
            doOutput(functionName, arguments, byRef, ostream);
            success = true;
        }
        finally {
            sendData(ostream, context, success);
        }
        Object result = null;
        InputStream istream = getInputStream(context);
        success = false;
        try {
            result = doInput(arguments, returnType, resultMode, istream);
            success = true;
        }
        finally {
            endInvoke(istream, context, success);
        }
        if (result instanceof HproseException) {
            throw (HproseException) result;
        }
        return result;
    }

    private Object doInput(Object[] arguments, Type returnType, HproseResultMode resultMode, InputStream istream) throws IOException {
        int tag;
        if (filter != null) {
            istream = filter.inputFilter(istream);
        }
        Object result = null;
        HproseReader hproseReader = new HproseReader(istream, mode);
        ByteArrayOutputStream bytestream = null;
        if (resultMode == HproseResultMode.RawWithEndTag || resultMode == HproseResultMode.Raw) {
            bytestream = new ByteArrayOutputStream();
        }
        while ((tag = hproseReader.checkTags(
                (char) HproseTags.TagResult + "" +
                (char) HproseTags.TagArgument + "" +
                (char) HproseTags.TagError + "" +
                (char) HproseTags.TagEnd)) != HproseTags.TagEnd) {
            switch (tag) {
                case HproseTags.TagResult:
                    if (resultMode == HproseResultMode.Normal) {
                        hproseReader.reset();
                        result = hproseReader.unserialize(returnType);
                    }
                    else if (resultMode == HproseResultMode.Serialized) {
                        result = hproseReader.readRaw();
                    }
                    else {
                        bytestream.write(HproseTags.TagResult);
                        hproseReader.readRaw(bytestream);
                    }
                    break;
                case HproseTags.TagArgument:
                    if (resultMode == HproseResultMode.RawWithEndTag || resultMode == HproseResultMode.Raw) {
                        bytestream.write(HproseTags.TagArgument);
                        hproseReader.readRaw(bytestream);
                    }
                    else {
                        hproseReader.reset();
                        Object[] args = hproseReader.readObjectArray();
                        System.arraycopy(args, 0, arguments, 0, arguments.length);
                    }
                    break;
                case HproseTags.TagError:
                    if (resultMode == HproseResultMode.RawWithEndTag || resultMode == HproseResultMode.Raw) {
                        bytestream.write(HproseTags.TagError);
                        hproseReader.readRaw(bytestream);
                    }
                    else {
                        hproseReader.reset();
                        result = new HproseException(hproseReader.readString());
                    }
                    break;
            }
        }
        if (resultMode == HproseResultMode.RawWithEndTag || resultMode == HproseResultMode.Raw) {
            if (resultMode == HproseResultMode.RawWithEndTag) {
                bytestream.write(HproseTags.TagEnd);
            }
            result = bytestream;
        }
        return result;
    }

    private void doOutput(String functionName, Object[] arguments, boolean byRef, OutputStream ostream) throws IOException {
        if (filter != null) {
            ostream = filter.outputFilter(ostream);
        }
        HproseWriter hproseWriter = new HproseWriter(ostream, mode);
        ostream.write(HproseTags.TagCall);
        hproseWriter.writeString(functionName);
        if ((arguments != null) && (arguments.length > 0 || byRef)) {
            hproseWriter.reset();
            hproseWriter.writeArray(arguments);
            if (byRef) {
                hproseWriter.writeBoolean(true);
            }
        }
        ostream.write(HproseTags.TagEnd);
    }

    protected abstract Object getInvokeContext() throws IOException;

    protected abstract OutputStream getOutputStream(Object context) throws IOException;

    protected abstract void sendData(OutputStream ostream, Object context, boolean success) throws IOException;

    protected abstract InputStream getInputStream(Object context) throws IOException;

    protected abstract void endInvoke(InputStream istream, Object context, boolean success) throws IOException;

}