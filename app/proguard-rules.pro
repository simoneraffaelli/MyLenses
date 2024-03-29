# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class sr.app.mylenses.utils.data.api.** { <fields>; }
-keep class sr.app.mylenses.utils.data.database.** { <fields>; }
-keep class sr.app.mylenses.utils.log.tree.loggly.** { <fields>; }
-keep class com.github.tony19.loggly.** { *; }

-keepclassmembers,allowobfuscation class * { @com.google.gson.annotations.SerializedName <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Retrofit  ----------
-keep class retrofit.** { *; }
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod, Annotation, *Annotation*

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * { @retrofit2.http.* <methods>; }

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keepclasseswithmembers class * { @retrofit2.http.* <methods>; }
-keepclassmembernames interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
##---------------End: proguard configuration for Retrofit  ----------

##---------------Begin: proguard configuration for DateTime  ----------

# Joda classes use the writeObject special method for Serializable, so
# if it's stripped, we'll run into NotSerializableExceptions.
# https://www.guardsquare.com/en/products/proguard/manual/examples#serializable
-keepnames class org.joda.** implements java.io.Serializable
-keepclassmembers class org.joda.** implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
##---------------End: proguard configuration for DateTime  ----------

-keep class com.google.appengine.api.urlfetch.** { <fields>; }
-keep class com.squareup.okhttp.Call.** { <fields>; }
-keep class org.bouncycastle.jsse.** { <fields>; }
-keep class org.conscrypt.** { <fields>; }
-keep class org.openjsse.** { <fields>; }
-keep class rx.Observable$OnSubscribe.** { <fields>; }
-keep class rx.Observable.** { <fields>; }
-dontwarn org.apache.http.**
-keep class org.apache.** {*;}
-keep class org.apache.http.** { *; }
-keep class android.net.http.AndroidHttpClient.** { *; }
-dontwarn android.net.http.**
-dontwarn com.google.appengine.api.urlfetch.HTTPHeader**
-dontwarn com.google.appengine.api.urlfetch.HTTPMethod**
-dontwarn com.google.appengine.api.urlfetch.HTTPRequest**
-dontwarn com.google.appengine.api.urlfetch.HTTPResponse**
-dontwarn com.google.appengine.api.urlfetch.URLFetchService**
-dontwarn com.google.appengine.api.urlfetch.URLFetchServiceFactory**
-dontwarn com.squareup.okhttp.Call**
-dontwarn com.squareup.okhttp.Headers**
-dontwarn com.squareup.okhttp.MediaType**
-dontwarn com.squareup.okhttp.OkHttpClient**
-dontwarn com.squareup.okhttp.Request$Builder**
-dontwarn com.squareup.okhttp.Request**
-dontwarn com.squareup.okhttp.RequestBody**
-dontwarn com.squareup.okhttp.Response**
-dontwarn com.squareup.okhttp.ResponseBody**
-dontwarn javax.servlet.ServletContextEvent**
-dontwarn javax.servlet.ServletContextListener**
-dontwarn org.apache.avalon.framework.logger.Logger**
-dontwarn org.apache.log.Hierarchy**
-dontwarn org.apache.log.Logger**
-dontwarn org.apache.log4j.Level**
-dontwarn org.apache.log4j.Logger**
-dontwarn org.apache.log4j.Priority**
-dontwarn org.bouncycastle.jsse.BCSSLParameters**
-dontwarn org.bouncycastle.jsse.BCSSLSocket**
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider**
-dontwarn org.conscrypt.Conscrypt$Version**
-dontwarn org.conscrypt.Conscrypt**
-dontwarn org.conscrypt.ConscryptHostnameVerifier**
-dontwarn org.joda.convert.FromString**
-dontwarn org.joda.convert.ToString**
-dontwarn org.openjsse.javax.net.ssl.SSLParameters**
-dontwarn org.openjsse.javax.net.ssl.SSLSocket**
-dontwarn org.openjsse.net.ssl.OpenJSSE**
-dontwarn rx.Observable$OnSubscribe**
-dontwarn rx.Observable**
-dontwarn rx.Subscriber**
-dontwarn rx.Subscription**
-dontwarn rx.subscriptions.Subscriptions**
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken
-keep class com.db.williamchart.** { *; }