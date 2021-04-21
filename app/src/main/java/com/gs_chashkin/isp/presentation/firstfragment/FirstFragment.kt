package com.gs_chashkin.isp.presentation.firstfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gs_chashkin.isp.R
import com.gs_chashkin.isp.isp_files.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Runnable
import java.util.*
import java.util.concurrent.TimeUnit


class FirstFragment : Fragment() {

    val size: Int = 999_99

    lateinit var getObservableFromList : Button
    lateinit var fromArray : Button
    lateinit var fromIterable : Button
    lateinit var updateWeather : Button
    lateinit var flow : Button
    lateinit var just : Button
    lateinit var intervalRange : Button
    lateinit var backPressure : Button
    lateinit var backPressureHandleFlowable : Button
    lateinit var subScribeOnUIChange : Button
    lateinit var viewFragment: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewFragment = inflater.inflate(R.layout.fragment_first, container, false)

        val coroutineJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Default + coroutineJob);
        val defaultScope = CoroutineScope(Dispatchers.Default + coroutineJob);

        getObservableFromList = viewFragment.findViewById<Button>(R.id.getObservableFromList)
        just = viewFragment.findViewById<Button>(R.id.just)
        fromArray = viewFragment.findViewById<Button>(R.id.fromArray)
        fromIterable = viewFragment.findViewById<Button>(R.id.fromIterable)
        updateWeather = viewFragment.findViewById<Button>(R.id.updateWeather)
        flow = viewFragment.findViewById<Button>(R.id.flow)
        intervalRange = viewFragment.findViewById<Button>(R.id.intervalRange)
        backPressure = viewFragment.findViewById<Button>(R.id.backPressure)
        backPressureHandleFlowable = viewFragment.findViewById<Button>(R.id.backPressureHandleFlowable)
        subScribeOnUIChange = viewFragment.findViewById<Button>(R.id.subScribeOnUIChange)


        Main.main(null)

        val observable = Observable.just(1, 2, 3, 4, 5, 6, 7)
        val observer = observable.subscribe {
            Log.v("RX", "$it")
        }

        getObservableFromList.setOnClickListener {
            getObservableFromList(listOf("Apple", "", "Orange", "Banana")).subscribe(
                { v -> println("Received form getObservableFromList: $v") },
                { e -> println("Error form getObservableFromList: $e") }
            )
        }


        just.setOnClickListener {
            Observable.just("Apple", "Orange", "Banana") // just превращает аргументы в Item
                .map { intput -> throw RuntimeException()}
                .subscribe(
                    { value -> println("Received: $value") }, // onNext
                    { error -> println("Error: $error") },    // onError
                    { println("Completed!") }                 // onComplete
                )
        }

        fromArray.setOnClickListener {
            Observable.fromArray("Apple", "Orange", "Banana") // под капотом реализоват через just()
                .subscribe(
                    { println(it) }
                )
        }

        fromIterable.setOnClickListener {
            Observable.fromIterable(listOf("Apple", "", "Orange", "Banana"))
                .subscribe(
                    { value -> println("Received: $value") },      // onNext
                    { error -> println("Error: $error") },         // onError
                    { println("Completed") }                       // onComplete
                )
        }

        flow.setOnClickListener{
            val flow = flow<String> {
                for (i in 1..10) {
                    emit("Hello World")
                    delay(1000L)
                }
            }



            GlobalScope.launch {
                flow.collect {
                    println(it)
                }
            }
        }

        updateWeather.setOnClickListener {
            val weatherStation = WeatherStation()
            weatherStation.addMan(Man("Georgii", "Chashkin"))
            weatherStation.addMan(Man("Ivan", "Ivanov"))
            weatherStation.addMan(Man("Stark", "Petrov"))

            weatherStation.updateWeather()
        }

        intervalRange.setOnClickListener {
            Observable.intervalRange(
                10L,     // Star начальное значение
                5L,      // Count количество
                0L,      // Initial Delay задержка начальная перед первой отдачей
                1L,      // Period задержка при emit
                TimeUnit.SECONDS // в чем измеряется задержка
            ).subscribe { println("Result we just received: $it") }
        }

        backPressure.setOnClickListener {
            val observable = PublishSubject.create<Int>()
            observable.observeOn(Schedulers.computation())
                .subscribe(
                    {
                        println("The Number Is: $it")
                    },
                    { t ->
                        print(t.message)
                    }
                )
            for (i in 0..1000000){
                observable.onNext(i)
            }
        }

        backPressureHandleFlowable.setOnClickListener {
            val observable = PublishSubject.create<Int>()
            observable
                .toFlowable(BackpressureStrategy.DROP)
                .observeOn(Schedulers.computation())
                .subscribe(
                    {
                        println("The Number Is: $it")
                    },
                    { t ->
                        print(t.message)
                    }
                )
            for (i in 0..1000000){
                observable.onNext(i)
            }
        }

        subScribeOnUIChange.setOnClickListener {
            Observable.just(1, 2, 3, 4, 5, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe() {
                    subScribeOnUIChange.text = it.toString()
                }
        }


        //rk koblin
        val list = listOf<String>("Apple", "Orange", "Banana")

        /**
        //Flow (interface) - async stream of data, emit some value.
        //Ends normally of with exeption
        // Flow - has intermediate operations (like Transformators in RxJava)
        // map/filter/zip/merge/take applied to UPSTREAM (полагаю, что это начало стрима) and
        // return downstream (окончание стрима, то что выдает значения?) where we can run other commands
        // Intermediate functions do not runs on in flow and not suspend functions (Следить за производительностью, долбоеб)
        //

        // Terminal operations collect/single/reduce/toList/launchIn - suspend functions that applys to our FLOW
        they start collect data from flow at given !!Scope!!
        They applyed to UPSTREAM and trigger execution of all operations (previous i guess)
        It also called collect FLOW. They run in suspending manner and never blocking thread
        Terminal operators ends normally or with exception, depends how all operations in flow ended up


        By default flows - sequentials in 1 coroutine except few operators for FLOW CONCURRENSY -
        buffer() - //TODO
        flatMapMerge() - //TODO

        By default all FLOWs represend as COld stream, that emits only when terminal operators called
        and can be called repeatedly
        For hot streams there subclass StateFlow. || Also flow can be converted to hot flow by
        stateIn
        shareIn
        or by converting Flow to !hot channel!! by
        produceIn
         */

        //FlowBuilders

        val flowOf = flowOf("My ass", "Dungeon")
        val asFlow = arrayOf("My ass").asFlow()
        val flow = flow<String> {
            //TODO
        }
        val channelFlow = channelFlow<String> {
            //TODO
        }

        //delivers value unconditionaly of its collectors
        val mutableStateFlow = MutableStateFlow("My ass")

        /**
         * State fllow never end normally neither as launchIn function
         * all collectors of State Flow called subscribers
         */

        /**
         * Note that flow preserves it on context unless
         * flowOn() is called
         * then all up's to flowOn(context) call will be executed on certain thread
         */

        //Hot flow that delivers value to all collectors
//        val mutableSharedFlow = MutableSharedFlow("My ass")

        flow.run {

        }


        val numbers = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val myNullListFlow = listOf(1, 2, 3, 4, null, 5).asFlow()



        uiScope.launch {

            numbers
                .buffer() // если эмиттер выдает слишком много данных, что коллектор не может
                // обработать - данные можно сохранить в буффере. (это buffer channel)
                // Он ограничен - можно получить по морде
                .flowOn(Dispatchers.Default)
                .collect {
                    //Выупск значений эмиттером зависит от наблюдателя,
                    // то есть если не вызван оператор collect()
                    //производство значений не начнется
                    // Так же это не блокирующий процесс, потому что он в стиле корутин, может быть отложен (suspend)
                    //
                    Log.v("FLOW", it.toString())
                    delay(1000)
                }
        }


        //flow builder
        val myFLow = flow {
            //do some implementation//
            emit(1)
        }

        uiScope.launch {
            myFLow.collect {
                Log.v("FlowBuilder", "flow{} obuilder $it")
            }
        }

        uiScope.launch {

            channelFlow {
                send(1)
            }
                .catch { execption -> Log.v("Erorr", "Do smth $execption") }
                .collect {

                }
        }

        /**
         * callbackFlow { } and channelFlow { } differense is
         *
         * We have to use callbackFlows when we need to wrap a callback on a Flow.
         *
         * We have to use channelFlows when we need concurrent flow emissions.
         *
         * also in implementation we use send() and offer() instead of emit and emitAll
         * To prevent channelFLow from stopping use awaitClose{ // } block
         * to explicictly say when and how channelFlow should stop
         */


        /**
         * Teminal oeprators
         */

        val myFlow = flow<Int> { /* */ }

        uiScope.launch {
//            myFlow.collect { /* */ }
//            myFlow.single()
//            myFlow.toList()//here we have destination as collection where whe want to hold data
//            val myLiveData = myFlow.asLiveData()  //need ktx lifecycle
        }




        val textview: TextView = viewFragment.findViewById(R.id.textview)
        val createArrayList: TextView = viewFragment.findViewById(R.id.createArrayList)

        textview.setOnClickListener {
            uiScope.launch {
                // будет ли жить uiScope если активити уничтожится? -Нет, но uiScope захвачен textview через лямбду и будет жить, пока жив объект textview
                for (i in 0..26) {
                    setMyIntArray(createIntArray())
                }
            }
        }

        createArrayList.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                setMyArrayList(createArrayList())
            }
        }

        //flatMapMerge operator

        val flowEven = flowOf(2, 4, 6, 8, 10)
        val flowOdd = flowOf(1, 3, 4, 7, 9)

        uiScope.launch {
            flowEven
                .flatMapMerge { intFromFlowEven ->
                    flowOf(intFromFlowEven * 100)
                }
                .collect {
                    Log.v("FlatMap", "$it")
                }
            flowEven
                .zip(flowOdd) { fE, fO ->
                    fE + fO
                }
                .collect {
                    Log.v("zip", "$it")
                }
            merge(flowOdd, flowEven)
                .collect {
                    Log.v("merge", "$it")
                }
        }


        /**
         * About Channels
         *
         * There is Sender
         * Buffer
         * Receiver
         *
         * Sender
         * send and offer - sends data to buffer. But offer send data when it is possible
         * and send sends data and if buffer full it is suspends
         *
         * Receiver
         * receive() and poll()
         * Poll gets data synchoronysly - when there is nothing to receive - gets null
         * Receive - asynchronously - if thre is nothing inside: the receive() caller gets suspended
         * until a new value is posted to the buffer
         *
         */


        val timer = viewFragment.findViewById<TextView>(R.id.timer)
        val timerStartButton = viewFragment.findViewById<Button>(R.id.timerStartButton)

        timerStartButton.setOnClickListener {
            val myRunnable = Runnable {
                timer.post {
                    while (true) {
                        val calendar: Calendar = Calendar.getInstance()
                        val hours: Int = calendar.get(Calendar.HOUR_OF_DAY)
                        val minutes: Int = calendar.get(Calendar.MINUTE)
                        val seconds: Int = calendar.get(Calendar.SECOND)
                        val time = "$hours:$minutes:$seconds"
                        timer.text = time
                    }
                }
            }
            val thread = Thread(myRunnable)
            thread.start()
        }


        return viewFragment

    }

    // функция которая создает из листа observable
    fun getObservableFromList(myList: List<String>) = Observable.create<String> { emitter ->
            myList.forEach { kind ->
                if (kind == "") {
                    emitter.onError(Exception("No value to show"))
                }
                emitter.onNext(kind)
            }
            emitter.onComplete()
        }

}

