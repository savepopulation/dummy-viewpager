# dummy-viewpager

A ViewPager Implementation for Clients' and POs' dummy requirements.

# How to use?

Just add <b>DummyViewPager</b> to your Xml.

```
<com.raqun.dummyviewpager.DummyViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:canScroll="true"
        app:duration="1000"
        app:velocity="5000" />
```

1- You can start sliding with:
```
dummyViewPager.startSliding()
// or
dummyViewPager.startSliding(1000)
```

2- You can stop sliding with:
```
dummyViewPager.stopSliding()
```

3- You can enable / disable scrolling with:
```
dummyViewPager.enableScrolling()
// or
dummyViewPager.disableScrolling()
```

# Dependency

```
maven { url 'https://jitpack.io' }
```

```
dependencies {
        compile 'com.github.savepopulation:dummy-viewpager:1.0'
}

```
