package com.creationunicorn.speedometer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.creationunicorn.speedometer.databinding.DialogLocationPermissionBinding
import com.creationunicorn.speedometer.databinding.DialogPermissionRequiredBinding
import com.creationunicorn.speedometer.ui.guide.GuideSettingActivity

object PermissionDialogHelper {

    fun showLocationPermissionDialog(
        context: Context,
        onGrantClicked: () -> Unit,
        onLaterClicked: () -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogLocationPermissionBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        binding.btnGrantNow.setOnClickListener {
            dialog.dismiss()
            onGrantClicked()
        }

        binding.btnLater.setOnClickListener {
            dialog.dismiss()
            onLaterClicked()
        }

        dialog.show()
    }

    fun showPermissionRequiredDialog(
        context: Context,
        onDismissed: () -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogPermissionRequiredBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        binding.ivCloseRequired.setOnClickListener {
            dialog.dismiss()
            onDismissed()
        }

        binding.btnAllowBattery.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, GuideSettingActivity::class.java).apply {
                putExtra(GuideSettingActivity.EXTRA_TYPE, GuideSettingActivity.TYPE_BATTERY)
            }
            context.startActivity(intent)
        }

        binding.btnAllowAutoStart.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, GuideSettingActivity::class.java).apply {
                putExtra(GuideSettingActivity.EXTRA_TYPE, GuideSettingActivity.TYPE_AUTOSTART)
            }
            context.startActivity(intent)
        }

        dialog.setOnDismissListener {
            onDismissed()
        }

        dialog.show()
    }
}
