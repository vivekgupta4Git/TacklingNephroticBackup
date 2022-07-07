package com.ruviapps.tacklingnephrotic

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapePath
import com.google.android.material.snackbar.Snackbar
import com.ruviapps.tacklingnephrotic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


//for dagger hilt , Activity class also need to annotate with entry point with fragment
@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.bottomAppBar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_welcome,
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_result), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launchWhenResumed {
            setUpDiamondFab()
        }

    }

    private fun setUpDiamondFab() {
        //Setting up diamond shaped bottom bar
        val bottomAppBar = binding.appBarMain.bottomAppBar
        val topEdge = BottomAppBarCutCornersTopEdge(
            bottomAppBar.fabCradleMargin,
            bottomAppBar.fabCradleRoundedCornerRadius,
            bottomAppBar.cradleVerticalOffset
        )
        val background = bottomAppBar.background as MaterialShapeDrawable
        background.shapeAppearanceModel =
            background.shapeAppearanceModel.toBuilder().setTopEdge(topEdge).build()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun setBottomAppBarVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        lifecycleScope.launchWhenStarted {
            binding.appBarMain.bottomAppBar.visibility = visibility

        }
    }

    fun setFabButtonVisibility(visibility: Int){
        lifecycleScope.launchWhenStarted {
            binding.appBarMain.fab.visibility = visibility

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


/**
 * Credit : https://github.com/material-components/material-components-android/blob/master/catalog/java/io/material/catalog/bottomappbar/BottomAppBarCutCornersTopEdge.java
 */
class BottomAppBarCutCornersTopEdge internal constructor(
    private val fabMargin: Float,
    roundedCornerRadius: Float,
    private val cradleVerticalOffset: Float,
) :
    BottomAppBarTopEdgeTreatment(fabMargin, roundedCornerRadius, cradleVerticalOffset) {
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath,
    ) {
        val fabDiameter = fabDiameter
        if (fabDiameter == 0f) {
            shapePath.lineTo(length, 0f)
            return
        }
        val diamondSize = fabDiameter / 2f
        val middle = center + horizontalOffset
        val verticalOffsetRatio = cradleVerticalOffset / diamondSize
        if (verticalOffsetRatio >= 1.0f) {
            shapePath.lineTo(length, 0f)
            return
        }
        shapePath.lineTo(middle - (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(middle, (diamondSize - cradleVerticalOffset + fabMargin) * interpolation)
        shapePath.lineTo(middle + (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(length, 0f)
    }
}