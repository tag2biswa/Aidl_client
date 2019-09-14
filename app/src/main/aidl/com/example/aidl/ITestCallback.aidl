// ITestCallback.aidl
package com.example.aidl;

import com.example.aidl.ITestInterface;

interface ITestCallback {
          void register(ITestInterface iTestInterface);
          void unregister(ITestInterface iTestInterface);
}
