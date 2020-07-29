// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : SInt72fixTo31_16_ROUNDTOINF
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79
// Date      : 20/07/2020, 11:11:12
// Designer	: Zhengpeng Liao



module SInt72fixTo31_16_ROUNDTOINF (
  input      [71:0]   din,
  output     [15:0]   dout
);
  wire       [72:0]   _zz_9;
  wire       [72:0]   _zz_10;
  wire       [15:0]   _zz_11;
  wire       [56:0]   _zz_12;
  wire       [56:0]   _zz_13;
  wire       [72:0]   _zz_14;
  wire       [72:0]   _zz_15;
  wire       [72:0]   _zz_16;
  wire       [41:0]   _zz_17;
  wire       [40:0]   _zz_18;
  reg        [56:0]   _zz_1;
  wire       [71:0]   _zz_2;
  wire       [71:0]   _zz_3;
  wire       [71:0]   _zz_4;
  wire       [72:0]   _zz_5;
  wire       [71:0]   _zz_6;
  reg        [56:0]   _zz_7;
  reg        [15:0]   _zz_8;

  assign _zz_9 = {_zz_4[71],_zz_4};
  assign _zz_10 = {_zz_3[71],_zz_3};
  assign _zz_11 = _zz_5[15 : 0];
  assign _zz_12 = _zz_5[72 : 16];
  assign _zz_13 = 57'h000000000000001;
  assign _zz_14 = ($signed(_zz_15) + $signed(_zz_16));
  assign _zz_15 = {_zz_6[71],_zz_6};
  assign _zz_16 = {_zz_2[71],_zz_2};
  assign _zz_17 = _zz_1[56 : 15];
  assign _zz_18 = _zz_1[55 : 15];
  assign _zz_2 = {{56'h0,1'b1},15'h0};
  assign _zz_3 = {57'h1ffffffffffffff,15'h0};
  assign _zz_4 = din[71 : 0];
  assign _zz_5 = ($signed(_zz_9) + $signed(_zz_10));
  assign _zz_6 = din[71 : 0];
  always @ (*) begin
    if((_zz_11 != 16'h0))begin
      _zz_7 = ($signed(_zz_12) + $signed(_zz_13));
    end else begin
      _zz_7 = _zz_5[72 : 16];
    end
  end

  always @ (*) begin
    if(_zz_5[72])begin
      _zz_1 = _zz_7;
    end else begin
      _zz_1 = (_zz_14 >>> 16);
    end
  end

  always @ (*) begin
    if(_zz_1[56])begin
      if((! (_zz_17 == 42'h3ffffffffff)))begin
        _zz_8 = 16'h8000;
      end else begin
        _zz_8 = _zz_1[15 : 0];
      end
    end else begin
      if((_zz_18 != 41'h0))begin
        _zz_8 = 16'h7fff;
      end else begin
        _zz_8 = _zz_1[15 : 0];
      end
    end
  end

  assign dout = _zz_8;

endmodule
