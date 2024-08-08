package com.example.calculator


import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
////////////////33333333333333333333333333333
    private var inputValue1: Double?=0.0
    private var inputValue2: Double?=null
    private var currentOperator: Operator?=null
    private var result: Double?=null
    private val equation: StringBuilder=StringBuilder().append(ZERO)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.status_bar, theme)
        setListeners()
        setNightModeIndicator()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }


    //////////// -------13---------
    private fun setListeners(){
        for (button in getNumbericButton()){

            button.setOnClickListener{onNumberClicked(button.text.toString())}
        }
        with(binding){
            buttonZero.setOnClickListener{onZeroClicked()}
            buttonDoubleZero.setOnClickListener { onDoubleZeroClicked() }
            buttonDecimalPoint.setOnClickListener { onDecimalPointClicked() }
            buttonAddition.setOnClickListener { onOperatorClicked(Operator.ADDITION) }
            buttonSubtraction.setOnClickListener { onOperatorClicked(Operator.SUBTRACTION) }
            buttonMultiplication.setOnClickListener { onOperatorClicked(Operator.MULTIPLICATION) }
            buttonDvision.setOnClickListener { onOperatorClicked(Operator.DIVISION) }
            buttonEquals.setOnClickListener { onEqualClicked()}
            buttonAllClear.setOnClickListener { onAllClearClicked() }
            buttonPlusMinus.setOnClickListener {onPlusMinusClicked()  }
            buttonPercentage.setOnClickListener { onPercentageClicked() }

            imageNightMode.setOnClickListener { toogleNightMode() }
        }


    }

    ////////-------------------23------------------------------
    private fun toogleNightMode(){

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()


    }



    ////////-------------------24------------------------------


    private fun setNightModeIndicator(){
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){


            binding.imageNightMode.setImageResource(R.drawable.light)
        }
        else {

            binding.imageNightMode.setImageResource(R.drawable.dark)
        }


    }


    ////////-------------------22-------------------------------

    private fun onPercentageClicked(){
        if(inputValue2==null){
            val percentage= getInputValur1()/100
            inputValue1=percentage
            equation.clear().append(percentage)
            updateInputOnDisplay()

        }
        else {
            val percentageOfValue1= (getInputValur1() *getInputValur2() )/100
            val percentageOfValue2=getInputValur2()/100
            result=when(requireNotNull(currentOperator)){
                Operator.ADDITION ->getInputValur1() + percentageOfValue1
                Operator.SUBTRACTION ->getInputValur1()  - percentageOfValue1
                Operator.MULTIPLICATION ->getInputValur1() * percentageOfValue2
                Operator.DIVISION->getInputValur1() / percentageOfValue2

            }
            equation.clear().append(ZERO)
            updateResultOnDisplay(isPercentage =true)
            inputValue1 =result
            result=null
            inputValue2=null
            currentOperator=null

        }


    }



    ////////-------------------21-------------------------------
    private fun onPlusMinusClicked(){

        if(equation.startsWith(MINUS)){
            equation.deleteCharAt(0)

        }
        else {
            equation.insert(0, MINUS)
        }
        setInput()
        updateInputOnDisplay()



    }


    ////////-------------------20--------------------------------

     private fun onAllClearClicked(){

         inputValue1= 0.0
         inputValue2= null
         currentOperator=null
         result=null
         equation.clear().append(ZERO)
         clearDisplay()

     }





    ////////-------------------19--------------------------------

    private fun onOperatorClicked(operator: Operator){
        onEqualClicked()
        currentOperator=operator



    }




    ////////-------------------18---------------------------------
     private fun onEqualClicked(){

         if (inputValue2 !=null){
             result= calculate()
             equation.clear().append(ZERO)
             updateResultOnDisplay()
             inputValue1 =result
             result=null
             inputValue2=null
             currentOperator=null


         }

        else{
            equation.clear().append(ZERO)
        }


     }




    ////////-------------------17---------------------------------

    private fun calculate():Double{
        return when(requireNotNull(currentOperator)){
            Operator.ADDITION  -> getInputValur1() + getInputValur2()
            Operator.SUBTRACTION  -> getInputValur1() - getInputValur2()
            Operator.MULTIPLICATION  -> getInputValur1() * getInputValur2()
            Operator.DIVISION -> getInputValur1() / getInputValur2()
        }


    }



    ////////-------------------16---------------------------------
    private fun onDecimalPointClicked(){

        if(equation.contains(DECIMAL_POINT)) return
        equation.append(DECIMAL_POINT)
        setInput()
        updateInputOnDisplay()


    }




    ////////-------------------14---------------------------------

    private fun onZeroClicked(){
        if (equation.startsWith(ZERO)) return
        onNumberClicked(ZERO)

    }


    ////////-------------------15---------------------------------

    private fun onDoubleZeroClicked(){
        if (equation.startsWith(ZERO)) return
        onNumberClicked(DOUBLE_ZERO)

    }


    //////////// -------12----------
    private fun getNumbericButton()= with(binding){

        arrayOf(
            buttonOne,
            buttonTwo,
            buttonThree,
            buttonFour,
            buttonFive,
            buttonSix,
            buttonSeven,
            buttonEight,
            buttonNine

        )

    }
    ///////////---11---------------
    private fun onNumberClicked(numberText: String){

        if(equation.startsWith(ZERO)) {
            equation.deleteCharAt(0)
        }
        else if(equation.startsWith("$MINUS$ZERO")){
            equation.deleteCharAt(1)

        }
        equation.append(numberText)
        setInput()
        updateInputOnDisplay()
    }




    /////////////10------------------------

    private fun setInput(){

        if(currentOperator ==null){

            inputValue1=equation.toString().toDouble()

        }
        else{
            inputValue2=equation.toString().toDouble()

        }

    }

    //////999999999999999999999999
    private fun clearDisplay(){

        with(binding){
            textInput.text= getFormattedDisplayValue(value = getInputValur1())
            textEquation.text=null

        }
    }


    /////////////888888888888888888
    private fun updateResultOnDisplay(isPercentage:Boolean=false){
        binding.textInput.text=getFormattedDisplayValue(value = result)
        var input2Text =getFormattedDisplayValue(value = getInputValur2())
        if(isPercentage) input2Text= "$input2Text${getString(R.string.percentage)}"
        binding.textEquation.text = String.format(
            "%s  %s %s",
            getFormattedDisplayValue(value = getInputValur1()),
            getOperatorSymbol(),
            input2Text

        )







    }

    //////////////777777777777777
    private fun updateInputOnDisplay(){
        if (result == null){

            binding.textEquation.text=null
        }

        binding.textInput.text=equation


    }


    ////////66666666666
    private fun getInputValur1()= inputValue1 ?: 0.0
    private fun getInputValur2()= inputValue2 ?: 0.0


    ///////////////////5555555555555555555
    private fun getOperatorSymbol(): String{

        return when (requireNotNull(currentOperator)){
            Operator.ADDITION -> getString(R.string.addition)
            Operator.SUBTRACTION -> getString(R.string.subtraction)
            Operator.MULTIPLICATION -> getString(R.string.multiplication)
            Operator.DIVISION -> getString(R.string.division)

        }
    }
/////////////4444444444444444444444444444
        private fun getFormattedDisplayValue(value:Double?):String?{
            val originalValue=value ?:return null
            return if (originalValue % 1 ==0.0){
                originalValue.toInt().toString()
            }
            else{
                originalValue.toString()
            }

        }



///////////111111111111111111111
    enum class Operator{

        ADDITION, SUBTRACTION, MULTIPLICATION,DIVISION;
    }
//////////////22222222222222222222
    private companion object{
        const val DECIMAL_POINT="."
        const val ZERO ="0"
        const val DOUBLE_ZERO="00"
        const val MINUS="-"
    }
}