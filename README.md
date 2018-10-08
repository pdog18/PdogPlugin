# PdogPlugin

这个是我学习 `Gradle Plugin` 的仓库

### default-manifest-attributes

通过`HOOK` `android-gradle-plugin` 的 `processManifest`在该`Task` 的`doLast` 中通过修改`AndroidManifest.xml` 文件，来为项目中的所有组件设置默认属性。

例如`sample` 中的为所有`activity` 设置一个默认的`screenOrientation` 属性

使用
```groovy
    // rootProject
    
    dependencies {
    //..
        classpath "com.pdog.plugin:default-manifest-attributes:0.1.0"
    }
```

```groovy
    //sampleProject
    apply plugin: 'default-attributes'
    activityAttributes {
        screenOrientation = "portrait"
    }
```

输出的`AndroidManifest.xml`

```xml
<!--默认的输出-->
<manifest package="com.pdog18.plugin" android:versionCode="1" xmlns:android="http://schemas.android.com/apk/res/android" android:versionName="1.0">
  <uses-sdk android:minSdkVersion="19" android:targetSdkVersion="27"/>
  <application android:allowBackup="true" android:debuggable="true" android:icon="@mipmap/ic_launcher" android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round" android:supportsRtl="true" android:theme="@style/AppTheme">
    <activity android:name="com.pdog18.plugin.EmptyScreenOrientationActivity" android:launchMode="singleTask" android:theme="@style/AppTheme" >
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnKotlinCodeActivity" />
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnManifestActivity" android:screenOrientation="portrait"/>
  </application>
</manifest>

```

```xml
<!--通过 default-manifest-attributes 设置了默认值后的输出-->
<manifest package="com.pdog18.plugin" android:versionCode="1" xmlns:android="http://schemas.android.com/apk/res/android" android:versionName="1.0">
  <uses-sdk android:minSdkVersion="19" android:targetSdkVersion="27"/>
  <application android:allowBackup="true" android:debuggable="true" android:icon="@mipmap/ic_launcher" android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round" android:supportsRtl="true" android:theme="@style/AppTheme">
    <activity android:name="com.pdog18.plugin.EmptyScreenOrientationActivity" android:launchMode="singleTask" android:theme="@style/AppTheme" android:screenOrientation="portrait">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnKotlinCodeActivity" android:screenOrientation="portrait"/>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnManifestActivity" android:screenOrientation="portrait"/>
  </application>
</manifest>

```