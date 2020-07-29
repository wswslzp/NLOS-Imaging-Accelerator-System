// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : SInt32fixTo23_8_ROUNDTOINF
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79
// Date      : 20/07/2020, 11:02:42



module SInt32fixTo23_8_ROUNDTOINF (
  input      [31:0]   din,
  output     [15:0]   dout
);
  wire       [32:0]   _zz_9;
  wire       [32:0]   _zz_10;
  wire       [7:0]    _zz_11;
  wire       [24:0]   _zz_12;
  wire       [24:0]   _zz_13;
  wire       [32:0]   _zz_14;
  wire       [32:0]   _zz_15;
  wire       [32:0]   _zz_16;
  wire       [9:0]    _zz_17;
  wire       [8:0]    _zz_18;
  reg        [24:0]   _zz_1;
  wire       [31:0]   _zz_2;
  wire       [31:0]   _zz_3;
  wire       [31:0]   _zz_4;
  wire       [32:0]   _zz_5;
  wire       [31:0]   _zz_6;
  reg        [24:0]   _zz_7;
  reg        [15:0]   _zz_8;

  assign _zz_9 = {_zz_4[31],_zz_4};
  assign _zz_10 = {_zz_3[31],_zz_3};
  assign _zz_11 = _zz_5[7 : 0];
  assign _zz_12 = _zz_5[32 : 8];
  assign _zz_13 = 25'h0000001;
  assign _zz_14 = ($signed(_zz_15) + $signed(_zz_16));
  assign _zz_15 = {_zz_6[31],_zz_6};
  assign _zz_16 = {_zz_2[31],_zz_2};
  assign _zz_17 = _zz_1[24 : 15];
  assign _zz_18 = _zz_1[23 : 15];
  assign _zz_2 = {{24'h0,1'b1},7'h0};
  assign _zz_3 = {25'h1ffffff,7'h0};
  assign _zz_4 = din[31 : 0];
  assign _zz_5 = ($signed(_zz_9) + $signed(_zz_10));
  assign _zz_6 = din[31 : 0];
  always @ (*) begin
    if((_zz_11 != 8'h0))begin
      _zz_7 = ($signed(_zz_12) + $signed(_zz_13));
    end else begin
      _zz_7 = _zz_5[32 : 8];
    end
  end

  always @ (*) begin
    if(_zz_5[32])begin
      _zz_1 = _zz_7;
    end else begin
      _zz_1 = (_zz_14 >>> 8);
    end
  end

  always @ (*) begin
    if(_zz_1[24])begin
      if((! (_zz_17 == 10'h3ff)))begin
        _zz_8 = 16'h8000;
      end else begin
        _zz_8 = _zz_1[15 : 0];
      end
    end else begin
      if((_zz_18 != 9'h0))begin
        _zz_8 = 16'h7fff;
      end else begin
        _zz_8 = _zz_1[15 : 0];
      end
    end
  end

  assign dout = _zz_8;

endmodule
