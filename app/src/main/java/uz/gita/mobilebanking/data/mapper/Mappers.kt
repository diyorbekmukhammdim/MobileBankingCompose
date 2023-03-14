package uz.gita.mobilebanking.data.mapper

import uz.gita.mobilebanking.data.model.StartScreenEnum

fun String.toStartScreenEnum(): StartScreenEnum =
    if (this == "HOME") StartScreenEnum.HOME
    else StartScreenEnum.LOGIN