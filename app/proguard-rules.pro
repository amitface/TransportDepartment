# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /opt/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn okio.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class com.citrus.** { *; }
-keepattributes *Annotation*

# For facebook crypto library
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
  -keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.DoNotStrip
   -keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.KeepGettersAndSetters
#
#    Do not strip any method/class that is annotated with @DoNotStrip
   -keep @com.facebook.crypto.proguard.annotations.DoNotStrip class *
   -keepclassmembers class * {
       @com.facebook.crypto.proguard.annotations.DoNotStrip *;
   }
#
  -keepclassmembers @com.facebook.crypto.proguard.annotations.KeepGettersAndSetters class * {
     void set*(***);
     *** get*();
   }
