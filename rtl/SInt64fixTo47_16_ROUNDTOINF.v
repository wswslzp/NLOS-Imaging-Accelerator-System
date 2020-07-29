// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : SInt64fixTo47_16_ROUNDTOINF
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79
// Date      : 20/07/2020, 11:11:11
// Designer	: Zhengpeng Liao



module SInt64fixTo47_16_ROUNDTOINF (
  input      [63:0]   din,
  output     [31:0]   dout
);
  wire       [64:0]   _zz_9;
  wire       [64:0]   _zz_10;
  wire       [15:0]   _zz_11;
  wire       [48:0]   _zz_12;
  wire       [48:0]   _zz_13;
  wire       [64:0]   _zz_14;
  wire       [64:0]   _zz_15;
  wire       [64:0]   _zz_16;
  wire       [17:0]   _zz_17;
  wire       [16:0]   _zz_18;
  reg        [48:0]   _zz_1;
  wire       [63:0]   _zz_2;
  wire       [63:0]   _zz_3;
  wire       [63:0]   _zz_4;
  wire       [64:0]   _zz_5;
  wire       [63:0]   _zz_6;
  reg        [48:0]   _zz_7;
  reg        [31:0]   _zz_8;

  assign _zz_9 = {_zz_4[63],_zz_4};
  assign _zz_10 = {_zz_3[63],_zz_3};
  assign _zz_11 = _zz_5[15 : 0];
  assign _zz_12 = _zz_5[64 : 16];
  assign _zz_13 = 49'h0000000000001;
  assign _zz_14 = ($signed(_zz_15) + $signed(_zz_16));
  assign _zz_15 = {_zz_6[63],_zz_6};
  assign _zz_16 = {_zz_2[63],_zz_2};
  assign _zz_17 = _zz_1[48 : 31];
  assign _zz_18 = _zz_1[47 : 31];
  assign _zz_2 = {{48'h0,1'b1},15'h0};
  assign _zz_3 = {49'h1ffffffffffff,15'h0};
  assign _zz_4 = din[63 : 0];
  assign _zz_5 = ($signed(_zz_9) + $signed(_zz_10));
  assign _zz_6 = din[63 : 0];
  always @ (*) begin
    if((_zz_11 != 16'h0))begin
      _zz_7 = ($signed(_zz_12) + $signed(_zz_13));
    end else begin
      _zz_7 = _zz_5[64 : 16];
    end
  end

  always @ (*) begin
    if(_zz_5[64])begin
      _zz_1 = _zz_7;
    end else begin
      _zz_1 = (_zz_14 >>> 16);
    end
  end

  always @ (*) begin
    if(_zz_1[48])begin
      if((! (_zz_17 == 18'h3ffff)))begin
        _zz_8 = 32'h80000000;
      end else begin
        _zz_8 = _zz_1[31 : 0];
      end
    end else begin
      if((_zz_18 != 17'h0))begin
        _zz_8 = 32'h7fffffff;
      end else begin
        _zz_8 = _zz_1[31 : 0];
      end
    end
  end

  assign dout = _zz_8;

endmodule
