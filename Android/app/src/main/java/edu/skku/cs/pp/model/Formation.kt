package edu.skku.cs.pp.model

import edu.skku.cs.pp.model.Position.*

data class Formation(val name: String, val positions: List<Position>)

val formations: List<Formation> = listOf(
    Formation("3-4-3", listOf(ST, LF, RF, LM, LCM, RCM, RM, LCB, CB, RCB, GK)),
    Formation("3-4-3(2)", listOf(ST, LW, RW, LM, LCM, RCM, RM, LCB, CB, RCB, GK)),
    Formation("3-4-1-2", listOf(LS, RS, CAM, LM, LCM, RCM, RM, LCB, CB, RCB, GK)),
    Formation("3-2-3-2", listOf(ST, CF, LM, CM, RM, LDM, RDM, LCB, CB, RCB, GK)),
    Formation("3-2-2-1-2", listOf(LS, RS, CAM, LM, RM, LDM, RDM, LCB, CB, RCB, GK)),
    Formation("3-1-2-3", listOf(LW, ST, RW, CAM, LM, RM, CDM, LCB, CB, RCB, GK)),
    Formation("3-1-4-2", listOf(LS, RS, LM, LCM, RCM, RM, CDM, LCB, CB, RCB, GK)),
    Formation("4-5-1", listOf(ST, LM, LCM, CM, RCM, RM, LB, LCB, RCB, RB, GK)),
    Formation("4-4-2", listOf(ST, CF, LM, LCM, RCM, RM, LB, LCB, RCB, RB, GK)),
    Formation("4-4-2(2)", listOf(LS, RS, LM, LCM, RCM, RM, LB, LCB, RCB, RB, GK)),
    Formation("4-4-1-1", listOf(ST, CAM, LM, LCM, RCM, RM, LB, LCB, RCB, RB, GK)),
    Formation("4-3-3", listOf(LW, ST, RW, LCM, CM, RCM, LB, LCB, RCB, RB, GK)),
    Formation("4-3-3(2)", listOf(ST, LF, RF, LCM, CM, RCM, LB, LCB, RCB, RB, GK)),
    Formation("4-3-2-1", listOf(ST, LM, LAM, CM, RAM, RM, LB, LCB, RCB, RB, GK)),
    Formation("4-3-1-2", listOf(LS, RS, CAM, LCM, CM, RCM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-4", listOf(LW, LW, RS, LW, LCM, RCM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-3-1", listOf(ST, LAM, CAM, RAM, LDM, RDM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-2-2", listOf(LS, RS, LM, RM, LDM, RDM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-2-2(2)", listOf(LS, RS, LAM, RAM, LDM, RDM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-2-1-1", listOf(ST, CAM, LM, RM, LDM, RDM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-1-3", listOf(LW, ST, RW, CAM, LCM, RCM, LB, LCB, RCB, RB, GK)),
    Formation("4-2-1-3(2)", listOf(LW, ST, RW, CM, LDM, RDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-4-1", listOf(ST, LM, LCM, RCM, RM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-3-2", listOf(LS, RS, LM, CM, RM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-2-3", listOf(LW, ST, RW, LCM, RCM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-2-3(2)", listOf(LW, RW, CF, LCM, RCM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-2-1-2", listOf(LS, RS, CAM, LM, RM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("4-1-2-1-2(2)", listOf(LS, RS, CAM, LCM, RCM, CDM, LB, LCB, RCB, RB, GK)),
    Formation("5-4-1", listOf(ST, LM, LCM, RCM, RM, LWB, LCB, CB, RCB, RWB, GK)),
    Formation("5-3-2", listOf(LS, RS, LCM, CM, RCM, LWB, LCB, CB, RCB, RWB, GK)),
    Formation("5-2-3", listOf(LW, ST, RW, LCM, RCM, LWB, LCB, CB, RCB, RWB, GK)),
    Formation("5-2-1-2", listOf(LS, RS, CAM, LCM, RCM, LWB, LCB, CB, RCB, RWB, GK)),
    Formation("5-1-2-1-1", listOf(ST, CAM, LM, RM, CDM, LWB, LCB, CB, RCB, RWB, GK)),
)