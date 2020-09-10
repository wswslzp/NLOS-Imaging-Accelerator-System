// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : SramForTest
// Git hash  : 5f24b07ffbfeb9934f5bea4487f4e9b5769de5ba



module SramForTest (
  input               io_CLK,
  output     [31:0]   io_Q,
  input      [31:0]   io_D,
  input      [13:0]   io_ADR,
  input      [3:0]    io_RM,
  input               io_WE,
  input               io_ME,
  input               io_RME,
  input               io_TEST1,
  input               io_LS,
  input               io_rst
);
  wire       [31:0]   internalSPMemSim_1__zz_2;

  InternalSPMemSim internalSPMemSim_1 (
    ._zz_1    (io_CLK                          ), //i
    ._zz_2    (internalSPMemSim_1__zz_2[31:0]  ), //o
    ._zz_3    (io_D[31:0]                      ), //i
    ._zz_4    (io_ADR[13:0]                    ), //i
    ._zz_5    (io_WE                           ), //i
    ._zz_6    (io_ME                           )  //i
  );
  assign io_Q = internalSPMemSim_1__zz_2;

endmodule

module InternalSPMemSim (
  input               _zz_1,
  output     [31:0]   _zz_2,
  input      [31:0]   _zz_3,
  input      [13:0]   _zz_4,
  input               _zz_5,
  input               _zz_6
);
  reg        [31:0]   _zz_8;
  wire       [31:0]   _zz_7;
  reg [31:0] clock_area_int_sim_mem [0:16383];

  always @ (posedge _zz_1) begin
    if(_zz_6) begin
      _zz_8 <= clock_area_int_sim_mem[_zz_4];
    end
  end

  always @ (posedge _zz_1) begin
    if(_zz_6 && _zz_5 ) begin
      clock_area_int_sim_mem[_zz_4] <= _zz_7;
    end
  end

  assign _zz_7 = _zz_3;
  assign _zz_2 = _zz_8;

endmodule
