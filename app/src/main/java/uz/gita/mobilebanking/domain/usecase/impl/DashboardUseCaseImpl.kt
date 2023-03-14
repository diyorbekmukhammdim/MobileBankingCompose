package uz.gita.mobilebanking.domain.usecase.impl

import uz.gita.mobilebanking.domain.usecase.DashboardUseCase
import uz.gita.mobilebanking.utils.ConnectionUtil
import javax.inject.Inject


class DashboardUseCaseImpl @Inject constructor(
    private val connect: ConnectionUtil
) : DashboardUseCase {


}
