import chisel3._
import chisel3.util.{log2Ceil, Counter}

class ControllerDigitCaps extends Module {
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

  io.accumulatorIsBiasControl := false.B

  val counter = new Counter(40)
  val round = RegInit(0.U(log2Ceil(3).W))
  val capsule = RegInit(0.U(log2Ceil(1152).W))

  when(!io.predictionVectorsReady) {
    io.storeInWeightCache := true.B
    io.weightMemAddress := (256 * 81 + 256 * 256 * 81).U
    io.sysArrRegControl := true.B
    io.sysArrAddControl := true.B
    io.sysArrIsWeightCache := true.B
    when(counter.value <= 19.U) {
      io.sysArrIn1 := capsule * 9.U
      io.sysArrIn2 := capsule * 9.U + (1 * 8).U
      io.sysArrIn3 := capsule * 9.U + (2 * 8).U
      io.sysArrIn4 := capsule * 9.U + (3 * 8).U
      io.sysArrIn5 := capsule * 9.U + (4 * 8).U
      io.sysArrIn6 := capsule * 9.U + (5 * 8).U
      io.sysArrIn7 := capsule * 9.U + (6 * 8).U
      io.sysArrIn8 := capsule * 9.U + (7 * 8).U
      io.sysArrIn9 := capsule * 9.U + (8 * 8).U
      io.sysArrWeightCache1 := capsule * 9.U
      io.sysArrWeightCache2 := capsule * 9.U + (1 * 8).U
      io.sysArrWeightCache3 := capsule * 9.U + (2 * 8).U
      io.sysArrWeightCache4 := capsule * 9.U + (3 * 8).U
      io.sysArrWeightCache5 := capsule * 9.U + (4 * 8).U
      io.sysArrWeightCache6 := capsule * 9.U + (5 * 8).U
      io.sysArrWeightCache7 := capsule * 9.U + (6 * 8).U
      io.sysArrWeightCache8 := capsule * 9.U + (7 * 8).U
      io.sysArrWeightCache9 := capsule * 9.U + (8 * 8).U
      io.isDataMemOrReuse := true.B
      counter.inc()
    }.otherwise {
      io.isDataMemOrReuse := false.B
      capsule := capsule + 1.U
      if (counter.value == 38.U) {
        io.saveSysArrInDataMem := true.B
        io.dataMemAddress := capsule * 8.U
        counter.reset()
      }
    }
  }

  when(capsule === 1151.U) {
    io.predictionVectorsReady := true.B
    io.isSoftmaxOrSquash := true.B
    capsule := 0.U
    round := 1.U
  }

  when(io.predictionVectorsReady && round === 1.U) {
    io.softmaxIsFirstRoundControl := true.B
    io.sysArrIsWeightCache := false.B
    io.isSoftmaxOrSquash := true.B
    io.accumulatorRegControl := true.B
    io.accumulatorAdderControl := true.B
    io.sysArrIn1 := capsule * 8.U
    io.sysArrIn2 := (capsule + 1.U) * 8.U
    io.sysArrIn3 := (capsule + 2.U) * 8.U
    io.sysArrIn4 := (capsule + 3.U) * 8.U
    io.sysArrIn5 := (capsule + 4.U) * 8.U
    io.sysArrIn6 := (capsule + 5.U) * 8.U
    io.sysArrIn7 := (capsule + 6.U) * 8.U
    io.sysArrIn8 := (capsule + 7.U) * 8.U
    io.sysArrIn9 := (capsule + 8.U) * 8.U
    capsule := capsule + 1.U
    if (capsule == 1151.U) {
      round := round + 1.U
      io.weightedSumReady := true.B
      if (counter.value == 10.U) {
        io.squashReady := true.B
      } else if (counter.value == 38.U) {
        io.agreementReady := true.B
      } else if (counter.value == 40.U) {
        io.updateBiasesReady := true.B
      } else {
        counter.inc()
      }
      capsule := 0.U
    }
  }.elsewhen(io.predictionVectorsReady && round > 1.U) {
    io.softmaxIsFirstRoundControl := false.B
    if (counter.value == 3.U) {
      io.softmaxReady := false.B
      io.softmaxExpoControl := true.B
      io.softmaxRegControl := true.B
      io.softmaxAdderControl := true.B
      counter.inc()
    } else {
      io.softmaxReady := true.B
      io.softmaxDividerControl := true.B
      counter.reset()
    }
    if (capsule == 1151.U) {
      round := round + 1.U
      io.weightedSumReady := true.B
      if (counter.value == 10.U) {
        io.squashReady := true.B
      } else if (counter.value == 38.U) {
        io.agreementReady := true.B
      } else if (counter.value == 40.U) {
        io.updateBiasesReady := true.B
      } else {
        counter.inc()
      }
      capsule := 0.U
    }
  }

  when(io.squashReady) {
    io.sysArrIsWeightCache := false.B
    io.isSoftmaxOrSquash := false.B
    io.sysArrIn1 := capsule * 8.U
    io.sysArrIn2 := (capsule + 1.U) * 8.U
    io.sysArrIn3 := (capsule + 2.U) * 8.U
    io.sysArrIn4 := (capsule + 3.U) * 8.U
    io.sysArrIn5 := (capsule + 4.U) * 8.U
    io.sysArrIn6 := (capsule + 5.U) * 8.U
    io.sysArrIn7 := (capsule + 6.U) * 8.U
    io.sysArrIn8 := (capsule + 7.U) * 8.U
    io.sysArrIn9 := (capsule + 8.U) * 8.U
  }

  when(round === 3.U && io.squashReady) {
    io.ready := true.B
  }

  io.dynamicRoutingMemAddress := capsule
}
