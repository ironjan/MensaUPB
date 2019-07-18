package de.ironjan.mensaupb.menus_ui


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.koushikdutta.ion.Ion
import de.ironjan.mensaupb.BuildConfig
import de.ironjan.mensaupb.R
import de.ironjan.mensaupb.api.model.Allergen
import de.ironjan.mensaupb.api.model.Badge
import de.ironjan.mensaupb.api.model.Menu
import de.ironjan.mensaupb.api.model.Restaurant
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Background
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.UiThread
import org.androidannotations.annotations.ViewById
import org.androidannotations.annotations.res.StringRes
import org.slf4j.LoggerFactory
import java.util.ArrayList
import java.util.Locale
import java.util.concurrent.ExecutionException

@EFragment(R.layout.fragment_menu_detail)
open class MenuDetailFragment : Fragment() {
    @ViewById
    internal lateinit var textName: TextView
    @ViewById
    internal lateinit var textCategory: TextView
    @ViewById
    internal lateinit var textAllergensHeader: TextView
    @ViewById
    internal lateinit var textAllergens: TextView
    @ViewById
    internal lateinit var textPrice: TextView
    @ViewById
    internal lateinit var textRestaurant: TextView
    @ViewById
    internal lateinit var textDate: TextView
    @ViewById
    internal lateinit var textBadges: TextView
    @ViewById
    internal lateinit var textDescription: TextView
    @ViewById
    internal lateinit var image: ImageView
    @ViewById
    internal lateinit var progressBar: ProgressBar
    @ViewById(android.R.id.progress)
    internal lateinit var indefiniteProgressBar: ProgressBar

    @StringRes
    internal lateinit var localizedDatePattern: String

    @AfterViews
    internal fun bindData() {
        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()")

        val menu: Menu = arguments!!.getParcelable(PARCEL_MENU)!!
        showMenu(menu)

        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData() done")
    }


    @UiThread
    internal open fun showError(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show()
    }

    private fun showMenu(menu: Menu) {
        bindMenuDataToViews(menu)
    }

    @UiThread
    internal open fun bindMenuDataToViews(menu: Menu) {
        val isEnglish = Locale.getDefault().language.startsWith(Locale.ENGLISH.toString())

        textName.text = menu.localizedName(isEnglish)
        textCategory.text = menu.localizedCategory(isEnglish)

        bindDescription(menu.localizedDescription(isEnglish))

        val activity = activity as AppCompatActivity?
        if (activity != null) {
            val supportActionBar = activity.supportActionBar
            if (supportActionBar != null) supportActionBar.title = ""
        }

        bindRestaurant(menu)
        bindDate(menu)
        bindPrice(menu)
        bindAllergens(menu)
        bindBadges(menu)
        loadImage(menu, false)
    }

    private fun bindDescription(description: String?) {
        if (TextUtils.isEmpty(description)) {
            textDescription.visibility = View.GONE
        } else {
            textDescription.text = description
        }
    }

    private fun bindBadges(stwMenu: Menu) {
        val badges = ArrayList<Badge>()

        val badgesAsString = stwMenu.badges
        for (badgeKey in badgesAsString) {
            badges.add(Badge.fromString(badgeKey))
        }

        if (badges.isEmpty()) {
            textBadges.visibility = View.GONE
            return
        }
        textBadges.visibility = View.VISIBLE
        val stringBuilder = StringBuilder(activity!!.getString(badges[0].stringId))
        for (i in 1 until badges.size) {
            val badgeString = activity!!.getString(badges[i].stringId)
            stringBuilder.append(", ")
                    .append(badgeString)
        }
        textBadges.text = stringBuilder.toString()
    }

    private fun bindRestaurant(stwMenu: Menu) {
        val restaurantId = stwMenu.restaurant
        val restaurantNameId = Restaurant.fromKey(restaurantId).restaurantName
        textRestaurant.setText(restaurantNameId)
    }

    private fun bindDate(stwMenu: Menu) {
        textDate.text = stwMenu.date
    }

    private fun bindPrice(stwMenu: Menu) {
        val price = stwMenu.priceStudents!!
        val priceAsString = String.format(Locale.GERMAN, "%.2f €", price)

        textPrice.text = priceAsString
        if (stwMenu.isWeighted) {
            textPrice.append("/100g")
        }
    }

    private fun bindAllergens(stwMenu: Menu) {
        val allergens = ArrayList<Allergen>()
        for (allergenKey in stwMenu.allergens) {
            allergens.add(Allergen.fromString(allergenKey))
        }
        if (allergens.isEmpty()) {
            hideAllergenList()
        } else {
            showAllergensList(allergens)
        }
    }

    private fun showAllergensList(allergens: List<Allergen>) {
        var notFirst = false
        val allergensListAsStringBuffer = StringBuilder()
        for (allergen in allergens) {
            if (notFirst) {
                allergensListAsStringBuffer.append("\n")
            } else {
                notFirst = true
            }
            val stringId = allergen.stringId
            val string = resources.getString(stringId)
            allergensListAsStringBuffer.append(string)
        }

        textAllergens.text = allergensListAsStringBuffer.toString()
        textAllergens.visibility = View.VISIBLE
        textAllergensHeader.visibility = View.VISIBLE
    }

    private fun hideAllergenList() {
        textAllergens.visibility = View.GONE
        textAllergensHeader.visibility = View.GONE
    }

    /**
     * Asynchronously load the image of the supplied menu
     *
     * @param stwMenu The menu to load a image for
     */
    @Background
    internal open fun loadImage(stwMenu: Menu, forced: Boolean) {
        setProgressVisibility(View.VISIBLE)

        LOGGER.debug("loadImage()")
        val start = System.currentTimeMillis()

        LOGGER.debug("loadImage() - start={}", start)

        val uri: String =
                if (!TextUtils.isEmpty(stwMenu.image)) {
            stwMenu.image
        } else {
            URI_NO_IMAGE_FILE
        }

        LOGGER.debug("loadImage() - URI set [{}]", start)

        try {
            val context = context ?: return

            val bitmap = Ion.with(context)
                    .load(uri)
                    .setLogging("MenuDetailFragment", Log.VERBOSE)
                    .progressBar(progressBar)
                    .asBitmap()
                    .get()
            applyLoadedImage(bitmap)
        } catch (e: InterruptedException) {
            applyErrorImage()
            LOGGER.error("InterruptedException: {}", e)
        } catch (e: ExecutionException) {
            applyErrorImage()
            LOGGER.error("InterruptedException: {}", e)
        }


        LOGGER.debug("loadImage() done")
    }

    @UiThread
    internal open fun setProgressVisibility(visible: Int) {
        progressBar.visibility = visible
        indefiniteProgressBar.visibility = visible
    }

    @UiThread
    internal open fun applyErrorImage() {
        Ion.with(activity!!)
                .load(URI_NO_IMAGE_FILE)
                .intoImageView(image)
        setProgressVisibility(View.GONE)
    }

    @UiThread
    internal open fun applyLoadedImage(bitmap: Bitmap) {
        image.setImageBitmap(bitmap)
        setProgressVisibility(View.GONE)
    }

    companion object {

        const val PARCEL_MENU = "PARCEL_MENU"

        const val URI_NO_IMAGE_FILE = "file:///android_asset/menu_has_no_image.png"
        private val LOGGER = LoggerFactory.getLogger(MenuDetailFragment::class.java.simpleName)

        fun newInstance(menu: Menu): MenuDetailFragment_ {
            if (BuildConfig.DEBUG)
                LOGGER.debug("newInstance({})", menu)

            val args = Bundle()
            args.putParcelable(PARCEL_MENU,menu)

            val menuDetailFragment_ = MenuDetailFragment_()
            menuDetailFragment_.arguments = args


            if (BuildConfig.DEBUG)
                LOGGER.debug("Created new MenuDetailFragment ({})", menu)

            if (BuildConfig.DEBUG)
                LOGGER.debug("newInstance({}) done", menu)

            return menuDetailFragment_
        }

    }

}
