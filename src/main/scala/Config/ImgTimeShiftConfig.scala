package Config

case class ImgTimeShiftConfig
(
  hComplexConfig: HComplexConfig,
  freq_factor: Int,
  depth_factor: Int,
  do_time_shift: Boolean = true, // default, compute the time-shift factor in the core.
  use_time_slice: Boolean = false, // means that depth channel transmit time slice instead direct depth
  use_phase_plane: Boolean = false // means that time-shift factor is already computed and send to the core
)
