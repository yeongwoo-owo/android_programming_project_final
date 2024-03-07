package edu.skku.cs.pp.model

import edu.skku.cs.pp.R
import edu.skku.cs.pp.model.PositionType.*

enum class Position(val id: Int, val type: PositionType, val view: Int) {
    GK(0, PositionType.GK, R.id.squad_position_gk),

    SW(1, DF, R.id.squad_position_sw),
    RWB(2, DF, R.id.squad_position_rwb),
    RB(3, DF, R.id.squad_position_rb),
    RCB(4, DF, R.id.squad_position_rcb),
    CB(5, DF, R.id.squad_position_cb),
    LCB(6, DF, R.id.squad_position_lcb),
    LB(7, DF, R.id.squad_position_lb),
    LWB(8, DF, R.id.squad_position_lwb),

    RDM(9, MF, R.id.squad_position_rdm),
    CDM(10, MF, R.id.squad_position_cdm),
    LDM(11, MF, R.id.squad_position_ldm),
    RM(12, MF, R.id.squad_position_rm),
    RCM(13, MF, R.id.squad_position_rcm),
    CM(14, MF, R.id.squad_position_cm),
    LCM(15, MF, R.id.squad_position_lcm),
    LM(16, MF, R.id.squad_position_lm),
    RAM(17, MF, R.id.squad_position_ram),
    CAM(18, MF, R.id.squad_position_cam),
    LAM(19, MF, R.id.squad_position_lam),

    RF(20, FW, R.id.squad_position_rf),
    CF(21, FW, R.id.squad_position_cf),
    LF(22, FW, R.id.squad_position_lf),
    RW(23, FW, R.id.squad_position_rw),
    RS(24, FW, R.id.squad_position_rs),
    ST(25, FW, R.id.squad_position_st),
    LS(26, FW, R.id.squad_position_ls),
    LW(27, FW, R.id.squad_position_lw)
}

enum class PositionType(val color: Int) {
    FW(R.color.fw_bg), MF(R.color.mf_bg), DF(R.color.df_bg), GK(R.color.gk_bg)
}