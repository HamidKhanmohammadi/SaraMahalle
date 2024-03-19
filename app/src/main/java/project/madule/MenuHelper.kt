package project.madule

import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import project.module.MethodBlock

fun Menu.addCategory(
    title: String,
    icon: Drawable? = null,
    props: MethodBlock<SubMenu> = {}
): SubMenu {
    return this.addSubMenu(title).apply {
        if (icon != null) {
            setIcon(icon)
        }

        props()
    }
}

fun Menu.addCategory(
    title: String,
    icon: GoogleMaterial.Icon,
    props: MethodBlock<SubMenu> = {}
): SubMenu {
    return this.addCategory(title, IconHelper.icon(icon), props)
}

fun SubMenu.addItem(
    title: String,
    icon: Drawable? = null,
    enabled: Boolean = true,
    onClick: MenuItem.() -> Boolean = { false }
): MenuItem {
    return this.add(title).apply {
        setEnabled(enabled)
        if (icon != null) {
            setIcon(icon)
        }

        setOnMenuItemClickListener {
            onClick()
        }
    }
}

fun SubMenu.addItem(
    title: String,
    icon: GoogleMaterial.Icon,
    enabled: Boolean = true,
    onClick: MenuItem.() -> Boolean = { false }
): MenuItem {
    return this.addItem(title, IconHelper.icon(icon), enabled, onClick)
}

fun Menu.addItem(
    title: String,
    icon: Drawable? = null,
    enabled: Boolean = true,
    onClick: MenuItem.() -> Boolean = { false }
): MenuItem {
    return this.add(title).apply {
        setEnabled(enabled)
        if (icon != null) {
            setIcon(icon)
        }

        setOnMenuItemClickListener {
            onClick()
        }
    }
}

fun Menu.addItem(
    title: String,
    icon: GoogleMaterial.Icon,
    enabled: Boolean = true,
    onClick: MenuItem.() -> Boolean = { false }
): MenuItem {
    return this.addItem(title, IconHelper.icon(icon), enabled, onClick)
}
