import chisel3._
import chisel3.util.{switch, is}
import chisel3.experimental.ChiselEnum

object Controller {
  object State extends ChiselEnum {
    val sConv1Controller, sPrimaryCapsController, sDigitCapsController = Value
  }
}

class Controller extends Module {
  import Controller.State
  import Controller.State._

  val io = IO(new Bundle {
    val dynamicRoutingMemAddress = Output(UInt(11.W))
    val softmaxExpoControl = Output(Bool())
    val softmaxRegControl = Output(Bool())
    val softmaxAdderControl = Output(Bool())
    val softmaxDividerControl = Output(Bool())
    val softmaxIsFirstRoundControl = Output(Bool())
    val sysArrIn1 = Output(UInt(17.W))
    val sysArrIn2 = Output(UInt(17.W))
    val sysArrIn3 = Output(UInt(17.W))
    val sysArrIn4 = Output(UInt(17.W))
    val sysArrIn5 = Output(UInt(17.W))
    val sysArrIn6 = Output(UInt(17.W))
    val sysArrIn7 = Output(UInt(17.W))
    val sysArrIn8 = Output(UInt(17.W))
    val sysArrIn9 = Output(UInt(17.W))
    val isDataMemOrReuse = Output(Bool())
    val sysArrRegControl = Output(Bool())
    val sysArrAddControl = Output(Bool())
    val sysArrIsWeightCache = Output(Bool())
    val sysArrWeightCache1 = Output(UInt(15.W))
    val sysArrWeightCache2 = Output(UInt(15.W))
    val sysArrWeightCache3 = Output(UInt(15.W))
    val sysArrWeightCache4 = Output(UInt(15.W))
    val sysArrWeightCache5 = Output(UInt(15.W))
    val sysArrWeightCache6 = Output(UInt(15.W))
    val sysArrWeightCache7 = Output(UInt(15.W))
    val sysArrWeightCache8 = Output(UInt(15.W))
    val sysArrWeightCache9 = Output(UInt(15.W))
    val isSoftmaxOrSquash = Output(Bool())
    val accumulatorBiasAddress = Output(UInt(9.W))
    val accumulatorRegControl = Output(Bool())
    val accumulatorAdderControl = Output(Bool())
    val accumulatorIsBiasControl = Output(Bool())
    val storeInWeightCache = Output(Bool())
    val weightMemAddress = Output(UInt(23.W))
    val saveReluInDataMem = Output(Bool())
    val saveSquashInDataMem = Output(Bool())
    val saveSysArrInDataMem = Output(Bool())
    val dataMemAddress = Output(UInt(17.W))
    val conv1Ready = Output(Bool())
    val primaryCapsReady = Output(Bool())
    val predictionVectorsReady = Output(Bool())
    val softmaxReady = Output(Bool())
    val weightedSumReady = Output(Bool())
    val squashReady = Output(Bool())
    val agreementReady = Output(Bool())
    val updateBiasesReady = Output(Bool())
    val ready = Output(Bool())
  })

  val state = RegInit(sConv1Controller)

  val conv1Controller = Module(new ControllerConv1)
  val primaryCapsController = Module(new ControllerPrimaryCaps)
  val digitCapsController = Module(new ControllerDigitCaps)

  switch(state) {
    is(sConv1Controller) {
      io.dynamicRoutingMemAddress := conv1Controller.io.dynamicRoutingMemAddress
      io.softmaxExpoControl := conv1Controller.io.softmaxExpoControl
      io.softmaxRegControl := conv1Controller.io.softmaxRegControl
      io.softmaxAdderControl := conv1Controller.io.softmaxAdderControl
      io.softmaxDividerControl := conv1Controller.io.softmaxDividerControl
      io.softmaxIsFirstRoundControl := conv1Controller.io.softmaxIsFirstRoundControl
      io.sysArrIn1 := conv1Controller.io.sysArrIn1
      io.sysArrIn2 := conv1Controller.io.sysArrIn2
      io.sysArrIn3 := conv1Controller.io.sysArrIn3
      io.sysArrIn4 := conv1Controller.io.sysArrIn4
      io.sysArrIn5 := conv1Controller.io.sysArrIn5
      io.sysArrIn6 := conv1Controller.io.sysArrIn6
      io.sysArrIn7 := conv1Controller.io.sysArrIn7
      io.sysArrIn8 := conv1Controller.io.sysArrIn8
      io.sysArrIn9 := conv1Controller.io.sysArrIn9
      io.isDataMemOrReuse := conv1Controller.io.isDataMemOrReuse
      io.sysArrRegControl := conv1Controller.io.sysArrRegControl
      io.sysArrAddControl := conv1Controller.io.sysArrAddControl
      io.sysArrIsWeightCache := conv1Controller.io.sysArrIsWeightCache
      io.sysArrWeightCache1 := conv1Controller.io.sysArrWeightCache1
      io.sysArrWeightCache2 := conv1Controller.io.sysArrWeightCache2
      io.sysArrWeightCache3 := conv1Controller.io.sysArrWeightCache3
      io.sysArrWeightCache4 := conv1Controller.io.sysArrWeightCache4
      io.sysArrWeightCache5 := conv1Controller.io.sysArrWeightCache5
      io.sysArrWeightCache6 := conv1Controller.io.sysArrWeightCache6
      io.sysArrWeightCache7 := conv1Controller.io.sysArrWeightCache7
      io.sysArrWeightCache8 := conv1Controller.io.sysArrWeightCache8
      io.sysArrWeightCache9 := conv1Controller.io.sysArrWeightCache9
      io.isSoftmaxOrSquash := conv1Controller.io.isSoftmaxOrSquash
      io.accumulatorBiasAddress := conv1Controller.io.accumulatorBiasAddress
      io.accumulatorRegControl := conv1Controller.io.accumulatorRegControl
      io.accumulatorAdderControl := conv1Controller.io.accumulatorAdderControl
      io.accumulatorIsBiasControl := conv1Controller.io.accumulatorIsBiasControl
      io.storeInWeightCache := conv1Controller.io.storeInWeightCache
      io.weightMemAddress := conv1Controller.io.weightMemAddress
      io.saveReluInDataMem := conv1Controller.io.saveReluInDataMem
      io.saveSquashInDataMem := conv1Controller.io.saveSquashInDataMem
      io.saveSysArrInDataMem := conv1Controller.io.saveSysArrInDataMem
      io.dataMemAddress := conv1Controller.io.dataMemAddress
      io.conv1Ready := conv1Controller.io.conv1Ready
      io.primaryCapsReady := conv1Controller.io.primaryCapsReady
      io.predictionVectorsReady := conv1Controller.io.predictionVectorsReady
      io.softmaxReady := conv1Controller.io.softmaxReady
      io.weightedSumReady := conv1Controller.io.weightedSumReady
      io.squashReady := conv1Controller.io.squashReady
      io.agreementReady := conv1Controller.io.agreementReady
      io.updateBiasesReady := conv1Controller.io.updateBiasesReady
      io.ready := conv1Controller.io.ready
      when(io.conv1Ready) {
        state := sPrimaryCapsController
      }
    }
    is(sPrimaryCapsController) {
      io.dynamicRoutingMemAddress := primaryCapsController.io.dynamicRoutingMemAddress
      io.softmaxExpoControl := primaryCapsController.io.softmaxExpoControl
      io.softmaxRegControl := primaryCapsController.io.softmaxRegControl
      io.softmaxAdderControl := primaryCapsController.io.softmaxAdderControl
      io.softmaxDividerControl := primaryCapsController.io.softmaxDividerControl
      io.softmaxIsFirstRoundControl := primaryCapsController.io.softmaxIsFirstRoundControl
      io.sysArrIn1 := primaryCapsController.io.sysArrIn1
      io.sysArrIn2 := primaryCapsController.io.sysArrIn2
      io.sysArrIn3 := primaryCapsController.io.sysArrIn3
      io.sysArrIn4 := primaryCapsController.io.sysArrIn4
      io.sysArrIn5 := primaryCapsController.io.sysArrIn5
      io.sysArrIn6 := primaryCapsController.io.sysArrIn6
      io.sysArrIn7 := primaryCapsController.io.sysArrIn7
      io.sysArrIn8 := primaryCapsController.io.sysArrIn8
      io.sysArrIn9 := primaryCapsController.io.sysArrIn9
      io.isDataMemOrReuse := primaryCapsController.io.isDataMemOrReuse
      io.sysArrRegControl := primaryCapsController.io.sysArrRegControl
      io.sysArrAddControl := primaryCapsController.io.sysArrAddControl
      io.sysArrIsWeightCache := primaryCapsController.io.sysArrIsWeightCache
      io.sysArrWeightCache1 := primaryCapsController.io.sysArrWeightCache1
      io.sysArrWeightCache2 := primaryCapsController.io.sysArrWeightCache2
      io.sysArrWeightCache3 := primaryCapsController.io.sysArrWeightCache3
      io.sysArrWeightCache4 := primaryCapsController.io.sysArrWeightCache4
      io.sysArrWeightCache5 := primaryCapsController.io.sysArrWeightCache5
      io.sysArrWeightCache6 := primaryCapsController.io.sysArrWeightCache6
      io.sysArrWeightCache7 := primaryCapsController.io.sysArrWeightCache7
      io.sysArrWeightCache8 := primaryCapsController.io.sysArrWeightCache8
      io.sysArrWeightCache9 := primaryCapsController.io.sysArrWeightCache9
      io.isSoftmaxOrSquash := primaryCapsController.io.isSoftmaxOrSquash
      io.accumulatorBiasAddress := primaryCapsController.io.accumulatorBiasAddress
      io.accumulatorRegControl := primaryCapsController.io.accumulatorRegControl
      io.accumulatorAdderControl := primaryCapsController.io.accumulatorAdderControl
      io.accumulatorIsBiasControl := primaryCapsController.io.accumulatorIsBiasControl
      io.storeInWeightCache := primaryCapsController.io.storeInWeightCache
      io.weightMemAddress := primaryCapsController.io.weightMemAddress
      io.saveReluInDataMem := primaryCapsController.io.saveReluInDataMem
      io.saveSquashInDataMem := primaryCapsController.io.saveSquashInDataMem
      io.saveSysArrInDataMem := primaryCapsController.io.saveSysArrInDataMem
      io.dataMemAddress := primaryCapsController.io.dataMemAddress
      io.conv1Ready := primaryCapsController.io.conv1Ready
      io.primaryCapsReady := primaryCapsController.io.primaryCapsReady
      io.predictionVectorsReady := primaryCapsController.io.predictionVectorsReady
      io.softmaxReady := primaryCapsController.io.softmaxReady
      io.weightedSumReady := primaryCapsController.io.weightedSumReady
      io.squashReady := primaryCapsController.io.squashReady
      io.agreementReady := primaryCapsController.io.agreementReady
      io.updateBiasesReady := primaryCapsController.io.updateBiasesReady
      io.ready := primaryCapsController.io.ready
      when(io.primaryCapsReady) {
        state := sDigitCapsController
      }
    }
    is(sDigitCapsController) {
      io.dynamicRoutingMemAddress := digitCapsController.io.dynamicRoutingMemAddress
      io.softmaxExpoControl := digitCapsController.io.softmaxExpoControl
      io.softmaxRegControl := digitCapsController.io.softmaxRegControl
      io.softmaxAdderControl := digitCapsController.io.softmaxAdderControl
      io.softmaxDividerControl := digitCapsController.io.softmaxDividerControl
      io.softmaxIsFirstRoundControl := digitCapsController.io.softmaxIsFirstRoundControl
      io.sysArrIn1 := digitCapsController.io.sysArrIn1
      io.sysArrIn2 := digitCapsController.io.sysArrIn2
      io.sysArrIn3 := digitCapsController.io.sysArrIn3
      io.sysArrIn4 := digitCapsController.io.sysArrIn4
      io.sysArrIn5 := digitCapsController.io.sysArrIn5
      io.sysArrIn6 := digitCapsController.io.sysArrIn6
      io.sysArrIn7 := digitCapsController.io.sysArrIn7
      io.sysArrIn8 := digitCapsController.io.sysArrIn8
      io.sysArrIn9 := digitCapsController.io.sysArrIn9
      io.isDataMemOrReuse := digitCapsController.io.isDataMemOrReuse
      io.sysArrRegControl := digitCapsController.io.sysArrRegControl
      io.sysArrAddControl := digitCapsController.io.sysArrAddControl
      io.sysArrIsWeightCache := digitCapsController.io.sysArrIsWeightCache
      io.sysArrWeightCache1 := digitCapsController.io.sysArrWeightCache1
      io.sysArrWeightCache2 := digitCapsController.io.sysArrWeightCache2
      io.sysArrWeightCache3 := digitCapsController.io.sysArrWeightCache3
      io.sysArrWeightCache4 := digitCapsController.io.sysArrWeightCache4
      io.sysArrWeightCache5 := digitCapsController.io.sysArrWeightCache5
      io.sysArrWeightCache6 := digitCapsController.io.sysArrWeightCache6
      io.sysArrWeightCache7 := digitCapsController.io.sysArrWeightCache7
      io.sysArrWeightCache8 := digitCapsController.io.sysArrWeightCache8
      io.sysArrWeightCache9 := digitCapsController.io.sysArrWeightCache9
      io.isSoftmaxOrSquash := digitCapsController.io.isSoftmaxOrSquash
      io.accumulatorBiasAddress := digitCapsController.io.accumulatorBiasAddress
      io.accumulatorRegControl := digitCapsController.io.accumulatorRegControl
      io.accumulatorAdderControl := digitCapsController.io.accumulatorAdderControl
      io.accumulatorIsBiasControl := digitCapsController.io.accumulatorIsBiasControl
      io.storeInWeightCache := digitCapsController.io.storeInWeightCache
      io.weightMemAddress := digitCapsController.io.weightMemAddress
      io.saveReluInDataMem := digitCapsController.io.saveReluInDataMem
      io.saveSquashInDataMem := digitCapsController.io.saveSquashInDataMem
      io.saveSysArrInDataMem := digitCapsController.io.saveSysArrInDataMem
      io.dataMemAddress := digitCapsController.io.dataMemAddress
      io.conv1Ready := digitCapsController.io.conv1Ready
      io.primaryCapsReady := digitCapsController.io.primaryCapsReady
      io.predictionVectorsReady := digitCapsController.io.predictionVectorsReady
      io.softmaxReady := digitCapsController.io.softmaxReady
      io.weightedSumReady := digitCapsController.io.weightedSumReady
      io.squashReady := digitCapsController.io.squashReady
      io.agreementReady := digitCapsController.io.agreementReady
      io.updateBiasesReady := digitCapsController.io.updateBiasesReady
      io.ready := digitCapsController.io.ready
    }
  }
}
