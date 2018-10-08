
Gradle Plugin's Repository

## merge-modules
组件化用于依赖 rootProject 下的所有 subProject 使用的 plugin ，配合DSL 使用


## default-manifest-attributes

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

输出的`AndroidManifest.xml` 对比, 留意两个文件中的 `android:screenOrientation="portrait"` 

```xml
<!--默认的输出-->
    <application>
    <activity android:name="com.pdog18.plugin.EmptyScreenOrientationActivity"  >
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnKotlinCodeActivity" />
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnManifestActivity" android:screenOrientation="portrait"/>
  </application>
```

```xml
<!--通过 default-manifest-attributes 设置了默认值后的输出-->
  <application>
    <activity android:name="com.pdog18.plugin.EmptyScreenOrientationActivity"  android:screenOrientation="portrait">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnKotlinCodeActivity" android:screenOrientation="portrait"/>
    <activity android:name="com.pdog18.plugin.ScreenOrientationOnManifestActivity" android:screenOrientation="portrait"/>
  </application>
```
