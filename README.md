**Android Jetpack** is a suite of libraries, tools, and architectural guidance designed to help developers build robust, maintainable, and feature-rich Android applications. Jetpack simplifies app development by handling many of the core, repetitive tasks, allowing developers to focus on building features that differentiate their applications.

---

### **Key Components of Android Jetpack**

Android Jetpack is divided into several component categories:

#### 1. **Foundation**
Core components and tools that support application consistency and functionality:
- **AppCompat**: Backward compatibility for modern Android UI components.
- **Android KTX**: Kotlin extensions to make Android development more concise and idiomatic.
- **Test**: Tools and libraries for testing Android apps, including JUnit, Espresso, and UI Automator.

#### 2. **Architecture**
Components to design robust, scalable, and testable apps:
- **ViewModel**: Retains UI-related data across configuration changes.
- **LiveData**: Observable data holder that respects the lifecycle of components.
- **Room**: SQLite object-mapping library for local database handling.
- **WorkManager**: Manage background work consistently.
- **DataStore**: Replaces SharedPreferences for storing small amounts of data in a structured and reactive way.
- **Navigation**: Simplifies implementing navigation and passing data between destinations.

#### 3. **Behavior**
Provides app services like notifications and permissions:
- **Media3**: Manage media playback.
- **Notifications**: Build, customize, and manage notifications.
- **Permissions**: Handle runtime permissions smoothly.
- **Sharing**: APIs for sharing content between apps.

#### 4. **UI**
Enhance the visual and interactive aspects of your app:
- **Fragment**: Modular sections of an activity’s UI.
- **RecyclerView**: Efficiently display large sets of data in a scrollable view.
- **ConstraintLayout**: Flexible layout manager for complex UI designs.
- **Jetpack Compose**: Modern toolkit for building native UI declaratively.
- **Paging**: Efficiently load and display paged data.
- **Animation**: APIs for creating dynamic UI transitions.

---

### **Advantages of Android Jetpack**
1. **Modern Design**: Encourages best practices like MVVM (Model-View-ViewModel) architecture.
2. **Backward Compatibility**: Supports older Android versions.
3. **Reduced Boilerplate Code**: Simplifies common tasks.
4. **Lifecycle Awareness**: Ensures that components operate safely within the Android lifecycle.
5. **Seamless Integration**: Designed to work well with Kotlin, Android's preferred programming language.

---

### **Example: Basic Jetpack Integration**
Here’s how to use **ViewModel** and **LiveData** in Jetpack:

#### Add Dependencies:
```gradle
dependencies {
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
}
```

#### Define a ViewModel:
```kotlin
class MyViewModel : ViewModel() {
    private val _text = MutableLiveData("Hello, Jetpack!")
    val text: LiveData<String> = _text
}
```

#### Use the ViewModel in an Activity:
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val textView: TextView = findViewById(R.id.textView)
        viewModel.text.observe(this) { newText ->
            textView.text = newText
        }
    }
}
```


### **What’s New in Jetpack?**
Jetpack is constantly evolving, with new components and updates being added to improve Android development. Recent updates include:
- **Jetpack Compose** for building UIs declaratively.
- **DataStore** for managing app data efficiently.
- **WorkManager improvements** for better background task management.
- 
---

### Reactive UI

Two ways to get reactive UI

**1** 
ViewModel +LiveData+ Observing the Live data in (activity) to update UI == Reactive UI

**2**
ViewModel +LiveData+ Data Binding(XML) == Reactive UI
Using the DataBinding you can avoid observing the LD in activity 
And direct linking data source with xml to be observed

### ViewMode  
ViewModel stores and provides data to the UI and also survives config. Change
 ViewModel helper class for the UI controller that is responsible for preparing data for the UI. 
VM is also Lifecycle aware ⇒ pass this (owner) in VMProvider
Help vm to destroy itself only when activity destroy not on Config change
We don’t create VM instance,ViewModelProvider factory create for us

**1 extend ViewModel**
Declare the data holdinging variables

**2**    
```kotlin
MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);
```
**3** 
```kotlin
model.getUsers().observe(this, users -> {
            // update UI        });
```

Pass the argument to VM using VMProviderFactory custom constructor
Implement the ViewModelprovider.NewInstanceFactory
Override the Create method
```kotlin
class UserListViewModelFactory(private val userInteractor: UserInteractor, private val customerInteractor: CustomerInteractor) :
       ViewModelProvider.NewInstanceFactory() {
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return UserListViewModel(userInteractor, customerInteractor) as T
   }
```

How to use Custom Provider Factory 
```kotlin
MyViewModel model = new ViewModelProvider(this,UserListViewModelFactory(new UserInteractor())).get(MyViewModel.class);
```
Other alternatives to pass arguments in VMProvider.NewInstanceFactory is to use Dependency injection Hilt
Declare instance of Repo and inject in ViewModel

AndroidViewmodel is (Avoid to pass context in VM) used when you need  context in Viewmodel, Activity can destroy on config change
Where as VM survive and lead to memory leak
Eg.
class WordViewModel(application: Application) : AndroidViewModel(application)


-Doesn’t survive the process shutdown
-Large amount of data all UI data, SI only small and important data 
Adv
-Share data between fragments
-Replace the Loader(keep ui updated) with the help of LiveData


---

### LiveData 
LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state. 
1 Declare

a)Private Mutable declared

b)Getter to get it’s value in Activity


Only readable form accessible, Vm only updates it. Whereas all others will observe it.
Private Fields + Public Accessors == Encapsulation
public class NameViewModel extends ViewModel {

Encapsulation
// Create a LiveData with a String
``` kotlin

private MutableLiveData<String> currentName;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

// Rest of the ViewModel...
}
``` 

2 Use
``` kotlin
vm.user.observe (this:Owner,Observer{user Return type-> tvUserName.setText(user.name)})


Observable field and RxJava are not lifecycle aware, Manually handle the lifecycle
currentName.setValue on front thread
currentName.postValue on background thread
```

### Type of LD
LiveData is abstract class not allowed to be changed

Mutable LD allow the to SetValue(on UI thread) and PostValue(Background thread)

Private Mutable LD and getter and setter methods to perform encapsulation, Observers are aware of any change

Meditor LD  allow to Observe number of LD under single observer
meditorLD.addSource(mFirstLD)
meditorLD.addSource(mSecondLD)

If any of these LD change, it’ observe is trigger(No need to handle multiple Observers)
Classes and interface used by lifecycle library is also used by framework classes
These classes are 

**Lifecycle** ⇒ Represent the lifecycle of Android component
**LifecycleOwner** ⇒ Component have lifecycle
**LifecycleObserver** ⇒ Who is Observing Lifecycle


Room directly return the LiveData object, Ui notified when something change in DB
Transformation
map() change LD output
switchMap swaps LD observer is listening to
MediatorLiveData 