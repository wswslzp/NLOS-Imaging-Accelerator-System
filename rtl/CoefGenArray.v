// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : CoefGenArray
// Git hash  : 66d14baee1e258a5224f385bb5bdec3071ea69cc
// Date      : 03/06/2020, 21:48:17



module CoefGenArray (
  input               io_wave_front_valid,
  input      [15:0]   io_wave_front_payload_0_0,
  input               io_distance_valid,
  input      [15:0]   io_distance_payload_0_0,
  input               io_timeshift_valid,
  input      [15:0]   io_timeshift_payload_0_0_real,
  input      [15:0]   io_timeshift_payload_0_0_imag,
  output              io_coef_valid,
  output     [15:0]   io_coef_payload_0_0_0_real,
  output     [15:0]   io_coef_payload_0_0_0_imag,
  input               clk,
  input               reset
);
  wire       [15:0]   coefGenCore_1_io_coef_real;
  wire       [15:0]   coefGenCore_1_io_coef_imag;
  reg        [15:0]   wave_front_0_0;
  reg        [15:0]   distance_0_0;
  reg        [15:0]   timeshift_0_0_real;
  reg        [15:0]   timeshift_0_0_imag;
  reg                 _zz_1;
  reg                 _zz_2;
  reg                 _zz_3;
  reg                 _zz_4;
  reg                 _zz_5;
  reg                 _zz_6;

  CoefGenCore coefGenCore_1 (
    .io_wave              (wave_front_0_0[15:0]              ), //i
    .io_distance          (distance_0_0[15:0]                ), //i
    .io_timeshift_real    (timeshift_0_0_real[15:0]          ), //i
    .io_timeshift_imag    (timeshift_0_0_imag[15:0]          ), //i
    .io_coef_real         (coefGenCore_1_io_coef_real[15:0]  ), //o
    .io_coef_imag         (coefGenCore_1_io_coef_imag[15:0]  ), //o
    .clk                  (clk                               ), //i
    .reset                (reset                             )  //i
  );
  assign io_coef_payload_0_0_0_real = coefGenCore_1_io_coef_real;
  assign io_coef_payload_0_0_0_imag = coefGenCore_1_io_coef_imag;
  assign io_coef_valid = _zz_6;
  always @ (posedge clk) begin
    if(io_wave_front_valid)begin
      wave_front_0_0 <= io_wave_front_payload_0_0;
    end
    if(io_distance_valid)begin
      distance_0_0 <= io_distance_payload_0_0;
    end
    if(io_timeshift_valid)begin
      timeshift_0_0_real <= io_timeshift_payload_0_0_real;
      timeshift_0_0_imag <= io_timeshift_payload_0_0_imag;
    end
    _zz_1 <= ((io_wave_front_valid && io_distance_valid) && io_timeshift_valid);
    _zz_2 <= _zz_1;
    _zz_3 <= _zz_2;
    _zz_4 <= _zz_3;
    _zz_5 <= _zz_4;
    _zz_6 <= _zz_5;
  end


endmodule

module CoefGenCore (
  input      [15:0]   io_wave,
  input      [15:0]   io_distance,
  input      [15:0]   io_timeshift_real,
  input      [15:0]   io_timeshift_imag,
  output     [15:0]   io_coef_real,
  output     [15:0]   io_coef_imag,
  input               clk,
  input               reset
);
  wire       [31:0]   _zz_21;
  wire       [31:0]   _zz_22;
  wire       [31:0]   _zz_23;
  wire       [31:0]   _zz_24;
  wire       [31:0]   _zz_25;
  wire       [63:0]   _zz_26;
  wire       [31:0]   _zz_27;
  wire       [31:0]   _zz_28;
  wire       [63:0]   _zz_29;
  wire       [71:0]   _zz_30;
  wire       [71:0]   _zz_31;
  wire       [71:0]   _zz_32;
  wire       [71:0]   _zz_33;
  wire       [31:0]   func_core_io_data_out_real;
  wire       [31:0]   func_core_io_data_out_imag;
  wire       [31:0]   fixTo_dout;
  wire       [31:0]   fixTo_1_dout;
  wire       [31:0]   fixTo_2_dout;
  wire       [31:0]   fixTo_3_dout;
  wire       [31:0]   fixTo_4_dout;
  wire       [31:0]   fixTo_5_dout;
  wire       [31:0]   fixTo_6_dout;
  wire       [31:0]   fixTo_7_dout;
  wire       [31:0]   fixTo_8_dout;
  wire       [71:0]   fixTo_9_dout;
  wire       [71:0]   fixTo_10_dout;
  wire       [15:0]   fixTo_11_dout;
  wire       [15:0]   fixTo_12_dout;
  wire                _zz_34;
  wire       [31:0]   _zz_35;
  wire       [31:0]   _zz_36;
  wire       [31:0]   _zz_37;
  wire       [31:0]   _zz_38;
  wire       [63:0]   _zz_39;
  wire       [31:0]   _zz_40;
  wire       [31:0]   _zz_41;
  wire       [31:0]   _zz_42;
  wire       [31:0]   _zz_43;
  wire       [63:0]   _zz_44;
  wire       [31:0]   _zz_45;
  wire       [31:0]   _zz_46;
  wire       [31:0]   _zz_47;
  wire       [31:0]   _zz_48;
  wire       [47:0]   _zz_49;
  wire       [15:0]   _zz_50;
  wire       [47:0]   _zz_51;
  wire       [15:0]   _zz_52;
  wire       [71:0]   _zz_53;
  wire       [71:0]   _zz_54;
  wire       [15:0]   _zz_55;
  wire       [15:0]   _zz_56;
  reg        [15:0]   wave;
  reg        [15:0]   distance;
  wire       [15:0]   timeshift_real;
  wire       [15:0]   timeshift_imag;
  reg        [15:0]   io_timeshift_regNext_real;
  reg        [15:0]   io_timeshift_regNext_imag;
  reg        [15:0]   _zz_1;
  reg        [15:0]   _zz_2;
  reg        [15:0]   _zz_3;
  reg        [15:0]   _zz_4;
  reg        [15:0]   _zz_5;
  reg        [15:0]   _zz_6;
  reg        [15:0]   _zz_7;
  reg        [15:0]   _zz_8;
  reg        [31:0]   wd_prod;
  wire       [15:0]   _zz_9;
  reg        [15:0]   wave_regNext;
  reg        [15:0]   _zz_10;
  reg        [15:0]   _zz_11;
  reg        [31:0]   _zz_12;
  reg        [31:0]   _zz_13;
  wire       [15:0]   _zz_14;
  wire       [31:0]   _zz_15;
  reg        [31:0]   exp_wd_prod_divw_real;
  reg        [31:0]   exp_wd_prod_divw_imag;
  wire       [47:0]   _zz_16;
  wire       [47:0]   _zz_17;
  wire       [47:0]   _zz_18;
  reg        [71:0]   _zz_19;
  reg        [71:0]   _zz_20;

  assign _zz_34 = (_zz_9 == 16'h0);
  assign _zz_35 = fixTo_2_dout;
  assign _zz_36 = 32'hffffffff;
  assign _zz_37 = 32'hffffffff;
  assign _zz_38 = fixTo_dout;
  assign _zz_39 = {fixTo_3_dout,_zz_40};
  assign _zz_40 = 32'h0;
  assign _zz_41 = _zz_15;
  assign _zz_42 = fixTo_5_dout;
  assign _zz_43 = fixTo_1_dout;
  assign _zz_44 = {fixTo_6_dout,_zz_45};
  assign _zz_45 = 32'h0;
  assign _zz_46 = _zz_15;
  assign _zz_47 = fixTo_8_dout;
  assign _zz_48 = ($signed(exp_wd_prod_divw_real) + $signed(exp_wd_prod_divw_imag));
  assign _zz_49 = ($signed(_zz_50) * $signed(exp_wd_prod_divw_imag));
  assign _zz_50 = ($signed(timeshift_real) + $signed(timeshift_imag));
  assign _zz_51 = ($signed(_zz_52) * $signed(exp_wd_prod_divw_real));
  assign _zz_52 = ($signed(timeshift_imag) - $signed(timeshift_real));
  assign _zz_53 = fixTo_9_dout;
  assign _zz_54 = fixTo_10_dout;
  assign _zz_55 = fixTo_11_dout;
  assign _zz_56 = fixTo_12_dout;
  ExpFunc func_core (
    .io_data_in          (wd_prod[31:0]                     ), //i
    .io_data_out_real    (func_core_io_data_out_real[31:0]  ), //o
    .io_data_out_imag    (func_core_io_data_out_imag[31:0]  ), //o
    .clk                 (clk                               ), //i
    .reset               (reset                             )  //i
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo (
    .din     (_zz_21[31:0]      ), //i
    .dout    (fixTo_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_1 (
    .din     (_zz_22[31:0]        ), //i
    .dout    (fixTo_1_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_2 (
    .din     (_zz_23[31:0]        ), //i
    .dout    (fixTo_2_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_3 (
    .din     (_zz_24[31:0]        ), //i
    .dout    (fixTo_3_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_4 (
    .din     (_zz_25[31:0]        ), //i
    .dout    (fixTo_4_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_5 (
    .din     (_zz_26[63:0]        ), //i
    .dout    (fixTo_5_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_6 (
    .din     (_zz_27[31:0]        ), //i
    .dout    (fixTo_6_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_7 (
    .din     (_zz_28[31:0]        ), //i
    .dout    (fixTo_7_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_8 (
    .din     (_zz_29[63:0]        ), //i
    .dout    (fixTo_8_dout[31:0]  )  //o
  );
  SInt72fixTo71_0_ROUNDTOINF fixTo_9 (
    .din     (_zz_30[71:0]        ), //i
    .dout    (fixTo_9_dout[71:0]  )  //o
  );
  SInt72fixTo71_0_ROUNDTOINF fixTo_10 (
    .din     (_zz_31[71:0]         ), //i
    .dout    (fixTo_10_dout[71:0]  )  //o
  );
  SInt72fixTo31_16_ROUNDTOINF fixTo_11 (
    .din     (_zz_32[71:0]         ), //i
    .dout    (fixTo_11_dout[15:0]  )  //o
  );
  SInt72fixTo31_16_ROUNDTOINF fixTo_12 (
    .din     (_zz_33[71:0]         ), //i
    .dout    (fixTo_12_dout[15:0]  )  //o
  );
  assign timeshift_real = _zz_7;
  assign timeshift_imag = _zz_8;
  assign _zz_9 = _zz_11;
  assign _zz_21 = func_core_io_data_out_real;
  assign _zz_22 = func_core_io_data_out_imag;
  assign _zz_14 = _zz_9;
  assign _zz_23 = {{(_zz_14[15] ? 8'hff : 8'h0),_zz_14},8'h0};
  assign _zz_15 = _zz_35[31 : 0];
  always @ (*) begin
    if(_zz_34)begin
      _zz_12 = _zz_36[31 : 0];
    end else begin
      _zz_12 = _zz_42[31 : 0];
    end
  end

  always @ (*) begin
    if(_zz_34)begin
      _zz_13 = _zz_37[31 : 0];
    end else begin
      _zz_13 = _zz_47[31 : 0];
    end
  end

  assign _zz_24 = _zz_38[31 : 0];
  assign _zz_25 = _zz_15;
  assign _zz_26 = ($signed(_zz_39) / $signed(_zz_41));
  assign _zz_27 = _zz_43[31 : 0];
  assign _zz_28 = _zz_15;
  assign _zz_29 = ($signed(_zz_44) / $signed(_zz_46));
  assign _zz_16 = ($signed(_zz_48) * $signed(timeshift_real));
  assign _zz_17 = ($signed(_zz_16) - $signed(_zz_49));
  assign _zz_30 = {(_zz_17[47] ? 24'hffffff : 24'h0),_zz_17};
  assign _zz_18 = ($signed(_zz_16) + $signed(_zz_51));
  assign _zz_31 = {(_zz_18[47] ? 24'hffffff : 24'h0),_zz_18};
  assign _zz_32 = _zz_19;
  assign io_coef_real = _zz_55[15 : 0];
  assign _zz_33 = _zz_20;
  assign io_coef_imag = _zz_56[15 : 0];
  always @ (posedge clk) begin
    wave <= io_wave;
    distance <= io_distance;
    io_timeshift_regNext_real <= io_timeshift_real;
    io_timeshift_regNext_imag <= io_timeshift_imag;
    _zz_1 <= io_timeshift_regNext_real;
    _zz_2 <= io_timeshift_regNext_imag;
    _zz_3 <= _zz_1;
    _zz_4 <= _zz_2;
    _zz_5 <= _zz_3;
    _zz_6 <= _zz_4;
    _zz_7 <= _zz_5;
    _zz_8 <= _zz_6;
    wd_prod <= ($signed(wave) * $signed(distance));
    wave_regNext <= wave;
    _zz_10 <= wave_regNext;
    _zz_11 <= _zz_10;
    exp_wd_prod_divw_real <= _zz_12;
    exp_wd_prod_divw_imag <= _zz_13;
    _zz_19 <= _zz_53[71 : 0];
    _zz_20 <= _zz_54[71 : 0];
  end


endmodule

//SInt72fixTo31_16_ROUNDTOINF replaced by SInt72fixTo31_16_ROUNDTOINF

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

//SInt72fixTo71_0_ROUNDTOINF replaced by SInt72fixTo71_0_ROUNDTOINF

module SInt72fixTo71_0_ROUNDTOINF (
  input      [71:0]   din,
  output     [71:0]   dout
);

  assign dout = din;

endmodule

//SInt64fixTo47_16_ROUNDTOINF replaced by SInt64fixTo47_16_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt64fixTo47_16_ROUNDTOINF replaced by SInt64fixTo47_16_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

module ExpFunc (
  input      [31:0]   io_data_in,
  output     [31:0]   io_data_out_real,
  output     [31:0]   io_data_out_imag,
  input               clk,
  input               reset
);
  wire       [31:0]   _zz_132;
  wire       [31:0]   _zz_133;
  wire       [31:0]   _zz_134;
  wire       [31:0]   _zz_135;
  wire       [31:0]   _zz_136;
  wire       [63:0]   _zz_137;
  wire       [31:0]   _zz_138;
  wire       [31:0]   _zz_139;
  wire       [63:0]   _zz_140;
  wire       [63:0]   _zz_141;
  wire       [63:0]   _zz_142;
  wire       [63:0]   _zz_143;
  wire       [63:0]   _zz_144;
  reg        [31:0]   _zz_145;
  reg        [31:0]   _zz_146;
  reg        [31:0]   _zz_147;
  reg        [31:0]   _zz_148;
  reg        [31:0]   _zz_149;
  reg        [31:0]   _zz_150;
  wire       [31:0]   fixTo_dout;
  wire       [31:0]   fixTo_1_dout;
  wire       [31:0]   fixTo_2_dout;
  wire       [31:0]   fixTo_3_dout;
  wire       [31:0]   fixTo_4_dout;
  wire       [31:0]   fixTo_5_dout;
  wire       [31:0]   fixTo_6_dout;
  wire       [31:0]   fixTo_7_dout;
  wire       [31:0]   fixTo_8_dout;
  wire       [31:0]   fixTo_9_dout;
  wire       [63:0]   fixTo_10_dout;
  wire       [63:0]   fixTo_11_dout;
  wire       [31:0]   fixTo_12_dout;
  wire       [31:0]   fixTo_13_dout;
  wire                _zz_151;
  wire                _zz_152;
  wire                _zz_153;
  wire       [31:0]   _zz_154;
  wire       [31:0]   _zz_155;
  wire       [31:0]   _zz_156;
  wire       [1:0]    _zz_157;
  wire       [0:0]    _zz_158;
  wire       [1:0]    _zz_159;
  wire       [0:0]    _zz_160;
  wire       [1:0]    _zz_161;
  wire       [2:0]    _zz_162;
  wire       [2:0]    _zz_163;
  wire       [1:0]    _zz_164;
  wire       [0:0]    _zz_165;
  wire       [1:0]    _zz_166;
  wire       [0:0]    _zz_167;
  wire       [1:0]    _zz_168;
  wire       [2:0]    _zz_169;
  wire       [3:0]    _zz_170;
  wire       [3:0]    _zz_171;
  wire       [1:0]    _zz_172;
  wire       [0:0]    _zz_173;
  wire       [1:0]    _zz_174;
  wire       [0:0]    _zz_175;
  wire       [1:0]    _zz_176;
  wire       [2:0]    _zz_177;
  wire       [2:0]    _zz_178;
  wire       [1:0]    _zz_179;
  wire       [0:0]    _zz_180;
  wire       [1:0]    _zz_181;
  wire       [0:0]    _zz_182;
  wire       [1:0]    _zz_183;
  wire       [2:0]    _zz_184;
  wire       [3:0]    _zz_185;
  wire       [4:0]    _zz_186;
  wire       [4:0]    _zz_187;
  wire       [1:0]    _zz_188;
  wire       [0:0]    _zz_189;
  wire       [1:0]    _zz_190;
  wire       [0:0]    _zz_191;
  wire       [1:0]    _zz_192;
  wire       [2:0]    _zz_193;
  wire       [2:0]    _zz_194;
  wire       [1:0]    _zz_195;
  wire       [0:0]    _zz_196;
  wire       [1:0]    _zz_197;
  wire       [0:0]    _zz_198;
  wire       [1:0]    _zz_199;
  wire       [2:0]    _zz_200;
  wire       [3:0]    _zz_201;
  wire       [3:0]    _zz_202;
  wire       [1:0]    _zz_203;
  wire       [0:0]    _zz_204;
  wire       [1:0]    _zz_205;
  wire       [0:0]    _zz_206;
  wire       [1:0]    _zz_207;
  wire       [2:0]    _zz_208;
  wire       [2:0]    _zz_209;
  wire       [1:0]    _zz_210;
  wire       [0:0]    _zz_211;
  wire       [1:0]    _zz_212;
  wire       [0:0]    _zz_213;
  wire       [1:0]    _zz_214;
  wire       [2:0]    _zz_215;
  wire       [3:0]    _zz_216;
  wire       [4:0]    _zz_217;
  wire       [5:0]    _zz_218;
  wire       [5:0]    _zz_219;
  wire       [1:0]    _zz_220;
  wire       [0:0]    _zz_221;
  wire       [1:0]    _zz_222;
  wire       [0:0]    _zz_223;
  wire       [1:0]    _zz_224;
  wire       [2:0]    _zz_225;
  wire       [2:0]    _zz_226;
  wire       [1:0]    _zz_227;
  wire       [0:0]    _zz_228;
  wire       [1:0]    _zz_229;
  wire       [0:0]    _zz_230;
  wire       [1:0]    _zz_231;
  wire       [2:0]    _zz_232;
  wire       [3:0]    _zz_233;
  wire       [3:0]    _zz_234;
  wire       [1:0]    _zz_235;
  wire       [0:0]    _zz_236;
  wire       [1:0]    _zz_237;
  wire       [0:0]    _zz_238;
  wire       [1:0]    _zz_239;
  wire       [2:0]    _zz_240;
  wire       [2:0]    _zz_241;
  wire       [1:0]    _zz_242;
  wire       [0:0]    _zz_243;
  wire       [1:0]    _zz_244;
  wire       [0:0]    _zz_245;
  wire       [1:0]    _zz_246;
  wire       [2:0]    _zz_247;
  wire       [3:0]    _zz_248;
  wire       [4:0]    _zz_249;
  wire       [4:0]    _zz_250;
  wire       [1:0]    _zz_251;
  wire       [0:0]    _zz_252;
  wire       [1:0]    _zz_253;
  wire       [0:0]    _zz_254;
  wire       [1:0]    _zz_255;
  wire       [2:0]    _zz_256;
  wire       [2:0]    _zz_257;
  wire       [1:0]    _zz_258;
  wire       [0:0]    _zz_259;
  wire       [1:0]    _zz_260;
  wire       [0:0]    _zz_261;
  wire       [1:0]    _zz_262;
  wire       [2:0]    _zz_263;
  wire       [3:0]    _zz_264;
  wire       [3:0]    _zz_265;
  wire       [1:0]    _zz_266;
  wire       [0:0]    _zz_267;
  wire       [1:0]    _zz_268;
  wire       [0:0]    _zz_269;
  wire       [1:0]    _zz_270;
  wire       [2:0]    _zz_271;
  wire       [2:0]    _zz_272;
  wire       [1:0]    _zz_273;
  wire       [0:0]    _zz_274;
  wire       [1:0]    _zz_275;
  wire       [0:0]    _zz_276;
  wire       [1:0]    _zz_277;
  wire       [2:0]    _zz_278;
  wire       [3:0]    _zz_279;
  wire       [4:0]    _zz_280;
  wire       [5:0]    _zz_281;
  wire       [6:0]    _zz_282;
  wire       [6:0]    _zz_283;
  wire       [1:0]    _zz_284;
  wire       [0:0]    _zz_285;
  wire       [1:0]    _zz_286;
  wire       [0:0]    _zz_287;
  wire       [1:0]    _zz_288;
  wire       [2:0]    _zz_289;
  wire       [2:0]    _zz_290;
  wire       [1:0]    _zz_291;
  wire       [0:0]    _zz_292;
  wire       [1:0]    _zz_293;
  wire       [0:0]    _zz_294;
  wire       [1:0]    _zz_295;
  wire       [2:0]    _zz_296;
  wire       [3:0]    _zz_297;
  wire       [3:0]    _zz_298;
  wire       [1:0]    _zz_299;
  wire       [0:0]    _zz_300;
  wire       [1:0]    _zz_301;
  wire       [0:0]    _zz_302;
  wire       [1:0]    _zz_303;
  wire       [2:0]    _zz_304;
  wire       [2:0]    _zz_305;
  wire       [1:0]    _zz_306;
  wire       [0:0]    _zz_307;
  wire       [1:0]    _zz_308;
  wire       [0:0]    _zz_309;
  wire       [1:0]    _zz_310;
  wire       [2:0]    _zz_311;
  wire       [3:0]    _zz_312;
  wire       [4:0]    _zz_313;
  wire       [4:0]    _zz_314;
  wire       [1:0]    _zz_315;
  wire       [0:0]    _zz_316;
  wire       [1:0]    _zz_317;
  wire       [0:0]    _zz_318;
  wire       [1:0]    _zz_319;
  wire       [2:0]    _zz_320;
  wire       [2:0]    _zz_321;
  wire       [1:0]    _zz_322;
  wire       [0:0]    _zz_323;
  wire       [1:0]    _zz_324;
  wire       [0:0]    _zz_325;
  wire       [1:0]    _zz_326;
  wire       [2:0]    _zz_327;
  wire       [3:0]    _zz_328;
  wire       [3:0]    _zz_329;
  wire       [1:0]    _zz_330;
  wire       [0:0]    _zz_331;
  wire       [1:0]    _zz_332;
  wire       [0:0]    _zz_333;
  wire       [1:0]    _zz_334;
  wire       [2:0]    _zz_335;
  wire       [2:0]    _zz_336;
  wire       [1:0]    _zz_337;
  wire       [0:0]    _zz_338;
  wire       [1:0]    _zz_339;
  wire       [0:0]    _zz_340;
  wire       [1:0]    _zz_341;
  wire       [2:0]    _zz_342;
  wire       [3:0]    _zz_343;
  wire       [4:0]    _zz_344;
  wire       [5:0]    _zz_345;
  wire       [5:0]    _zz_346;
  wire       [1:0]    _zz_347;
  wire       [0:0]    _zz_348;
  wire       [1:0]    _zz_349;
  wire       [0:0]    _zz_350;
  wire       [1:0]    _zz_351;
  wire       [2:0]    _zz_352;
  wire       [2:0]    _zz_353;
  wire       [1:0]    _zz_354;
  wire       [0:0]    _zz_355;
  wire       [1:0]    _zz_356;
  wire       [0:0]    _zz_357;
  wire       [1:0]    _zz_358;
  wire       [2:0]    _zz_359;
  wire       [3:0]    _zz_360;
  wire       [3:0]    _zz_361;
  wire       [1:0]    _zz_362;
  wire       [0:0]    _zz_363;
  wire       [1:0]    _zz_364;
  wire       [0:0]    _zz_365;
  wire       [1:0]    _zz_366;
  wire       [2:0]    _zz_367;
  wire       [2:0]    _zz_368;
  wire       [1:0]    _zz_369;
  wire       [0:0]    _zz_370;
  wire       [1:0]    _zz_371;
  wire       [0:0]    _zz_372;
  wire       [1:0]    _zz_373;
  wire       [2:0]    _zz_374;
  wire       [3:0]    _zz_375;
  wire       [4:0]    _zz_376;
  wire       [4:0]    _zz_377;
  wire       [1:0]    _zz_378;
  wire       [0:0]    _zz_379;
  wire       [1:0]    _zz_380;
  wire       [0:0]    _zz_381;
  wire       [1:0]    _zz_382;
  wire       [2:0]    _zz_383;
  wire       [2:0]    _zz_384;
  wire       [1:0]    _zz_385;
  wire       [0:0]    _zz_386;
  wire       [1:0]    _zz_387;
  wire       [0:0]    _zz_388;
  wire       [1:0]    _zz_389;
  wire       [2:0]    _zz_390;
  wire       [3:0]    _zz_391;
  wire       [3:0]    _zz_392;
  wire       [1:0]    _zz_393;
  wire       [0:0]    _zz_394;
  wire       [1:0]    _zz_395;
  wire       [0:0]    _zz_396;
  wire       [1:0]    _zz_397;
  wire       [2:0]    _zz_398;
  wire       [2:0]    _zz_399;
  wire       [1:0]    _zz_400;
  wire       [0:0]    _zz_401;
  wire       [1:0]    _zz_402;
  wire       [0:0]    _zz_403;
  wire       [1:0]    _zz_404;
  wire       [2:0]    _zz_405;
  wire       [3:0]    _zz_406;
  wire       [4:0]    _zz_407;
  wire       [5:0]    _zz_408;
  wire       [6:0]    _zz_409;
  wire       [7:0]    _zz_410;
  wire       [7:0]    _zz_411;
  wire       [1:0]    _zz_412;
  wire       [0:0]    _zz_413;
  wire       [1:0]    _zz_414;
  wire       [0:0]    _zz_415;
  wire       [1:0]    _zz_416;
  wire       [2:0]    _zz_417;
  wire       [2:0]    _zz_418;
  wire       [1:0]    _zz_419;
  wire       [0:0]    _zz_420;
  wire       [1:0]    _zz_421;
  wire       [0:0]    _zz_422;
  wire       [1:0]    _zz_423;
  wire       [2:0]    _zz_424;
  wire       [3:0]    _zz_425;
  wire       [3:0]    _zz_426;
  wire       [1:0]    _zz_427;
  wire       [0:0]    _zz_428;
  wire       [1:0]    _zz_429;
  wire       [0:0]    _zz_430;
  wire       [1:0]    _zz_431;
  wire       [2:0]    _zz_432;
  wire       [2:0]    _zz_433;
  wire       [1:0]    _zz_434;
  wire       [0:0]    _zz_435;
  wire       [1:0]    _zz_436;
  wire       [0:0]    _zz_437;
  wire       [1:0]    _zz_438;
  wire       [2:0]    _zz_439;
  wire       [3:0]    _zz_440;
  wire       [4:0]    _zz_441;
  wire       [4:0]    _zz_442;
  wire       [1:0]    _zz_443;
  wire       [0:0]    _zz_444;
  wire       [1:0]    _zz_445;
  wire       [0:0]    _zz_446;
  wire       [1:0]    _zz_447;
  wire       [2:0]    _zz_448;
  wire       [2:0]    _zz_449;
  wire       [1:0]    _zz_450;
  wire       [0:0]    _zz_451;
  wire       [1:0]    _zz_452;
  wire       [0:0]    _zz_453;
  wire       [1:0]    _zz_454;
  wire       [2:0]    _zz_455;
  wire       [3:0]    _zz_456;
  wire       [3:0]    _zz_457;
  wire       [1:0]    _zz_458;
  wire       [0:0]    _zz_459;
  wire       [1:0]    _zz_460;
  wire       [0:0]    _zz_461;
  wire       [1:0]    _zz_462;
  wire       [2:0]    _zz_463;
  wire       [2:0]    _zz_464;
  wire       [1:0]    _zz_465;
  wire       [0:0]    _zz_466;
  wire       [1:0]    _zz_467;
  wire       [0:0]    _zz_468;
  wire       [1:0]    _zz_469;
  wire       [2:0]    _zz_470;
  wire       [3:0]    _zz_471;
  wire       [4:0]    _zz_472;
  wire       [5:0]    _zz_473;
  wire       [5:0]    _zz_474;
  wire       [1:0]    _zz_475;
  wire       [0:0]    _zz_476;
  wire       [1:0]    _zz_477;
  wire       [0:0]    _zz_478;
  wire       [1:0]    _zz_479;
  wire       [2:0]    _zz_480;
  wire       [2:0]    _zz_481;
  wire       [1:0]    _zz_482;
  wire       [0:0]    _zz_483;
  wire       [1:0]    _zz_484;
  wire       [0:0]    _zz_485;
  wire       [1:0]    _zz_486;
  wire       [2:0]    _zz_487;
  wire       [3:0]    _zz_488;
  wire       [3:0]    _zz_489;
  wire       [1:0]    _zz_490;
  wire       [0:0]    _zz_491;
  wire       [1:0]    _zz_492;
  wire       [0:0]    _zz_493;
  wire       [1:0]    _zz_494;
  wire       [2:0]    _zz_495;
  wire       [2:0]    _zz_496;
  wire       [1:0]    _zz_497;
  wire       [0:0]    _zz_498;
  wire       [1:0]    _zz_499;
  wire       [0:0]    _zz_500;
  wire       [1:0]    _zz_501;
  wire       [2:0]    _zz_502;
  wire       [3:0]    _zz_503;
  wire       [4:0]    _zz_504;
  wire       [4:0]    _zz_505;
  wire       [1:0]    _zz_506;
  wire       [0:0]    _zz_507;
  wire       [1:0]    _zz_508;
  wire       [0:0]    _zz_509;
  wire       [1:0]    _zz_510;
  wire       [2:0]    _zz_511;
  wire       [2:0]    _zz_512;
  wire       [1:0]    _zz_513;
  wire       [0:0]    _zz_514;
  wire       [1:0]    _zz_515;
  wire       [0:0]    _zz_516;
  wire       [1:0]    _zz_517;
  wire       [2:0]    _zz_518;
  wire       [3:0]    _zz_519;
  wire       [3:0]    _zz_520;
  wire       [1:0]    _zz_521;
  wire       [0:0]    _zz_522;
  wire       [1:0]    _zz_523;
  wire       [0:0]    _zz_524;
  wire       [1:0]    _zz_525;
  wire       [2:0]    _zz_526;
  wire       [2:0]    _zz_527;
  wire       [1:0]    _zz_528;
  wire       [0:0]    _zz_529;
  wire       [1:0]    _zz_530;
  wire       [0:0]    _zz_531;
  wire       [1:0]    _zz_532;
  wire       [2:0]    _zz_533;
  wire       [3:0]    _zz_534;
  wire       [4:0]    _zz_535;
  wire       [5:0]    _zz_536;
  wire       [6:0]    _zz_537;
  wire       [6:0]    _zz_538;
  wire       [1:0]    _zz_539;
  wire       [0:0]    _zz_540;
  wire       [1:0]    _zz_541;
  wire       [0:0]    _zz_542;
  wire       [1:0]    _zz_543;
  wire       [2:0]    _zz_544;
  wire       [2:0]    _zz_545;
  wire       [1:0]    _zz_546;
  wire       [0:0]    _zz_547;
  wire       [1:0]    _zz_548;
  wire       [0:0]    _zz_549;
  wire       [1:0]    _zz_550;
  wire       [2:0]    _zz_551;
  wire       [3:0]    _zz_552;
  wire       [3:0]    _zz_553;
  wire       [1:0]    _zz_554;
  wire       [0:0]    _zz_555;
  wire       [1:0]    _zz_556;
  wire       [0:0]    _zz_557;
  wire       [1:0]    _zz_558;
  wire       [2:0]    _zz_559;
  wire       [2:0]    _zz_560;
  wire       [1:0]    _zz_561;
  wire       [0:0]    _zz_562;
  wire       [1:0]    _zz_563;
  wire       [0:0]    _zz_564;
  wire       [1:0]    _zz_565;
  wire       [2:0]    _zz_566;
  wire       [3:0]    _zz_567;
  wire       [4:0]    _zz_568;
  wire       [4:0]    _zz_569;
  wire       [1:0]    _zz_570;
  wire       [0:0]    _zz_571;
  wire       [1:0]    _zz_572;
  wire       [0:0]    _zz_573;
  wire       [1:0]    _zz_574;
  wire       [2:0]    _zz_575;
  wire       [2:0]    _zz_576;
  wire       [1:0]    _zz_577;
  wire       [0:0]    _zz_578;
  wire       [1:0]    _zz_579;
  wire       [0:0]    _zz_580;
  wire       [1:0]    _zz_581;
  wire       [2:0]    _zz_582;
  wire       [3:0]    _zz_583;
  wire       [3:0]    _zz_584;
  wire       [1:0]    _zz_585;
  wire       [0:0]    _zz_586;
  wire       [1:0]    _zz_587;
  wire       [0:0]    _zz_588;
  wire       [1:0]    _zz_589;
  wire       [2:0]    _zz_590;
  wire       [2:0]    _zz_591;
  wire       [1:0]    _zz_592;
  wire       [0:0]    _zz_593;
  wire       [1:0]    _zz_594;
  wire       [0:0]    _zz_595;
  wire       [1:0]    _zz_596;
  wire       [2:0]    _zz_597;
  wire       [3:0]    _zz_598;
  wire       [4:0]    _zz_599;
  wire       [5:0]    _zz_600;
  wire       [5:0]    _zz_601;
  wire       [1:0]    _zz_602;
  wire       [0:0]    _zz_603;
  wire       [1:0]    _zz_604;
  wire       [0:0]    _zz_605;
  wire       [1:0]    _zz_606;
  wire       [2:0]    _zz_607;
  wire       [2:0]    _zz_608;
  wire       [1:0]    _zz_609;
  wire       [0:0]    _zz_610;
  wire       [1:0]    _zz_611;
  wire       [0:0]    _zz_612;
  wire       [1:0]    _zz_613;
  wire       [2:0]    _zz_614;
  wire       [3:0]    _zz_615;
  wire       [3:0]    _zz_616;
  wire       [1:0]    _zz_617;
  wire       [0:0]    _zz_618;
  wire       [1:0]    _zz_619;
  wire       [0:0]    _zz_620;
  wire       [1:0]    _zz_621;
  wire       [2:0]    _zz_622;
  wire       [2:0]    _zz_623;
  wire       [1:0]    _zz_624;
  wire       [0:0]    _zz_625;
  wire       [1:0]    _zz_626;
  wire       [0:0]    _zz_627;
  wire       [1:0]    _zz_628;
  wire       [2:0]    _zz_629;
  wire       [3:0]    _zz_630;
  wire       [4:0]    _zz_631;
  wire       [4:0]    _zz_632;
  wire       [1:0]    _zz_633;
  wire       [0:0]    _zz_634;
  wire       [1:0]    _zz_635;
  wire       [0:0]    _zz_636;
  wire       [1:0]    _zz_637;
  wire       [2:0]    _zz_638;
  wire       [2:0]    _zz_639;
  wire       [1:0]    _zz_640;
  wire       [0:0]    _zz_641;
  wire       [1:0]    _zz_642;
  wire       [0:0]    _zz_643;
  wire       [1:0]    _zz_644;
  wire       [2:0]    _zz_645;
  wire       [3:0]    _zz_646;
  wire       [3:0]    _zz_647;
  wire       [1:0]    _zz_648;
  wire       [0:0]    _zz_649;
  wire       [1:0]    _zz_650;
  wire       [0:0]    _zz_651;
  wire       [1:0]    _zz_652;
  wire       [2:0]    _zz_653;
  wire       [2:0]    _zz_654;
  wire       [1:0]    _zz_655;
  wire       [0:0]    _zz_656;
  wire       [1:0]    _zz_657;
  wire       [0:0]    _zz_658;
  wire       [1:0]    _zz_659;
  wire       [2:0]    _zz_660;
  wire       [3:0]    _zz_661;
  wire       [4:0]    _zz_662;
  wire       [5:0]    _zz_663;
  wire       [6:0]    _zz_664;
  wire       [7:0]    _zz_665;
  wire       [31:0]   _zz_666;
  wire       [31:0]   _zz_667;
  wire       [31:0]   _zz_668;
  wire       [31:0]   _zz_669;
  wire       [31:0]   _zz_670;
  wire       [31:0]   _zz_671;
  wire       [63:0]   _zz_672;
  wire       [31:0]   _zz_673;
  wire       [31:0]   _zz_674;
  wire       [31:0]   _zz_675;
  wire       [31:0]   _zz_676;
  wire       [63:0]   _zz_677;
  wire       [31:0]   _zz_678;
  wire       [31:0]   _zz_679;
  wire       [31:0]   _zz_680;
  wire       [31:0]   _zz_681;
  wire       [63:0]   _zz_682;
  wire       [63:0]   _zz_683;
  wire       [31:0]   _zz_684;
  wire       [63:0]   _zz_685;
  wire       [63:0]   _zz_686;
  wire       [31:0]   _zz_687;
  wire       [63:0]   _zz_688;
  wire       [63:0]   _zz_689;
  wire       [47:0]   _zz_690;
  wire       [63:0]   _zz_691;
  wire       [63:0]   _zz_692;
  wire       [63:0]   _zz_693;
  wire       [47:0]   _zz_694;
  wire       [63:0]   _zz_695;
  wire       [31:0]   _zz_696;
  wire       [31:0]   _zz_697;
  wire       [31:0]   fx_period;
  wire       [31:0]   _zz_1;
  reg        [31:0]   indx;
  wire       [31:0]   pfunc_tb_0_real;
  wire       [31:0]   pfunc_tb_0_imag;
  wire       [31:0]   pfunc_tb_1_real;
  wire       [31:0]   pfunc_tb_1_imag;
  wire       [31:0]   pfunc_tb_2_real;
  wire       [31:0]   pfunc_tb_2_imag;
  wire       [31:0]   pfunc_tb_3_real;
  wire       [31:0]   pfunc_tb_3_imag;
  wire       [31:0]   pfunc_tb_4_real;
  wire       [31:0]   pfunc_tb_4_imag;
  wire       [31:0]   pfunc_tb_5_real;
  wire       [31:0]   pfunc_tb_5_imag;
  wire       [31:0]   pfunc_tb_6_real;
  wire       [31:0]   pfunc_tb_6_imag;
  wire       [31:0]   pfunc_tb_7_real;
  wire       [31:0]   pfunc_tb_7_imag;
  wire       [31:0]   pfunc_tb_8_real;
  wire       [31:0]   pfunc_tb_8_imag;
  wire       [31:0]   pfunc_tb_9_real;
  wire       [31:0]   pfunc_tb_9_imag;
  wire       [31:0]   pfunc_tb_10_real;
  wire       [31:0]   pfunc_tb_10_imag;
  wire       [31:0]   pfunc_tb_11_real;
  wire       [31:0]   pfunc_tb_11_imag;
  wire       [31:0]   pfunc_tb_12_real;
  wire       [31:0]   pfunc_tb_12_imag;
  wire       [31:0]   pfunc_tb_13_real;
  wire       [31:0]   pfunc_tb_13_imag;
  wire       [31:0]   pfunc_tb_14_real;
  wire       [31:0]   pfunc_tb_14_imag;
  wire       [31:0]   pfunc_tb_15_real;
  wire       [31:0]   pfunc_tb_15_imag;
  wire       [31:0]   pfunc_tb_16_real;
  wire       [31:0]   pfunc_tb_16_imag;
  wire       [31:0]   pfunc_tb_17_real;
  wire       [31:0]   pfunc_tb_17_imag;
  wire       [31:0]   pfunc_tb_18_real;
  wire       [31:0]   pfunc_tb_18_imag;
  wire       [31:0]   pfunc_tb_19_real;
  wire       [31:0]   pfunc_tb_19_imag;
  wire       [31:0]   pfunc_tb_20_real;
  wire       [31:0]   pfunc_tb_20_imag;
  wire       [31:0]   pfunc_tb_21_real;
  wire       [31:0]   pfunc_tb_21_imag;
  wire       [31:0]   pfunc_tb_22_real;
  wire       [31:0]   pfunc_tb_22_imag;
  wire       [31:0]   pfunc_tb_23_real;
  wire       [31:0]   pfunc_tb_23_imag;
  wire       [31:0]   pfunc_tb_24_real;
  wire       [31:0]   pfunc_tb_24_imag;
  wire       [31:0]   pfunc_tb_25_real;
  wire       [31:0]   pfunc_tb_25_imag;
  wire       [31:0]   pfunc_tb_26_real;
  wire       [31:0]   pfunc_tb_26_imag;
  wire       [31:0]   pfunc_tb_27_real;
  wire       [31:0]   pfunc_tb_27_imag;
  wire       [31:0]   pfunc_tb_28_real;
  wire       [31:0]   pfunc_tb_28_imag;
  wire       [31:0]   pfunc_tb_29_real;
  wire       [31:0]   pfunc_tb_29_imag;
  wire       [31:0]   pfunc_tb_30_real;
  wire       [31:0]   pfunc_tb_30_imag;
  wire       [31:0]   pfunc_tb_31_real;
  wire       [31:0]   pfunc_tb_31_imag;
  wire       [31:0]   pfunc_tb_32_real;
  wire       [31:0]   pfunc_tb_32_imag;
  wire       [31:0]   pfunc_tb_33_real;
  wire       [31:0]   pfunc_tb_33_imag;
  wire       [31:0]   pfunc_tb_34_real;
  wire       [31:0]   pfunc_tb_34_imag;
  wire       [31:0]   pfunc_tb_35_real;
  wire       [31:0]   pfunc_tb_35_imag;
  wire       [31:0]   pfunc_tb_36_real;
  wire       [31:0]   pfunc_tb_36_imag;
  wire       [31:0]   pfunc_tb_37_real;
  wire       [31:0]   pfunc_tb_37_imag;
  wire       [31:0]   pfunc_tb_38_real;
  wire       [31:0]   pfunc_tb_38_imag;
  wire       [31:0]   pfunc_tb_39_real;
  wire       [31:0]   pfunc_tb_39_imag;
  wire       [31:0]   pfunc_tb_40_real;
  wire       [31:0]   pfunc_tb_40_imag;
  wire       [31:0]   pfunc_tb_41_real;
  wire       [31:0]   pfunc_tb_41_imag;
  wire       [31:0]   pfunc_tb_42_real;
  wire       [31:0]   pfunc_tb_42_imag;
  wire       [31:0]   pfunc_tb_43_real;
  wire       [31:0]   pfunc_tb_43_imag;
  wire       [31:0]   pfunc_tb_44_real;
  wire       [31:0]   pfunc_tb_44_imag;
  wire       [31:0]   pfunc_tb_45_real;
  wire       [31:0]   pfunc_tb_45_imag;
  wire       [31:0]   pfunc_tb_46_real;
  wire       [31:0]   pfunc_tb_46_imag;
  wire       [31:0]   pfunc_tb_47_real;
  wire       [31:0]   pfunc_tb_47_imag;
  wire       [31:0]   pfunc_tb_48_real;
  wire       [31:0]   pfunc_tb_48_imag;
  wire       [31:0]   pfunc_tb_49_real;
  wire       [31:0]   pfunc_tb_49_imag;
  wire       [31:0]   pfunc_tb_50_real;
  wire       [31:0]   pfunc_tb_50_imag;
  wire       [31:0]   pfunc_tb_51_real;
  wire       [31:0]   pfunc_tb_51_imag;
  wire       [31:0]   pfunc_tb_52_real;
  wire       [31:0]   pfunc_tb_52_imag;
  wire       [31:0]   pfunc_tb_53_real;
  wire       [31:0]   pfunc_tb_53_imag;
  wire       [31:0]   pfunc_tb_54_real;
  wire       [31:0]   pfunc_tb_54_imag;
  wire       [31:0]   pfunc_tb_55_real;
  wire       [31:0]   pfunc_tb_55_imag;
  wire       [31:0]   pfunc_tb_56_real;
  wire       [31:0]   pfunc_tb_56_imag;
  wire       [31:0]   pfunc_tb_57_real;
  wire       [31:0]   pfunc_tb_57_imag;
  wire       [31:0]   pfunc_tb_58_real;
  wire       [31:0]   pfunc_tb_58_imag;
  wire       [31:0]   pfunc_tb_59_real;
  wire       [31:0]   pfunc_tb_59_imag;
  wire       [31:0]   pfunc_tb_60_real;
  wire       [31:0]   pfunc_tb_60_imag;
  wire       [31:0]   pfunc_tb_61_real;
  wire       [31:0]   pfunc_tb_61_imag;
  wire       [31:0]   pfunc_tb_62_real;
  wire       [31:0]   pfunc_tb_62_imag;
  wire       [31:0]   pfunc_tb_63_real;
  wire       [31:0]   pfunc_tb_63_imag;
  wire       [31:0]   pfunc_tb_64_real;
  wire       [31:0]   pfunc_tb_64_imag;
  wire       [31:0]   pfunc_tb_65_real;
  wire       [31:0]   pfunc_tb_65_imag;
  wire       [31:0]   pfunc_tb_66_real;
  wire       [31:0]   pfunc_tb_66_imag;
  wire       [31:0]   pfunc_tb_67_real;
  wire       [31:0]   pfunc_tb_67_imag;
  wire       [31:0]   pfunc_tb_68_real;
  wire       [31:0]   pfunc_tb_68_imag;
  wire       [31:0]   pfunc_tb_69_real;
  wire       [31:0]   pfunc_tb_69_imag;
  wire       [31:0]   pfunc_tb_70_real;
  wire       [31:0]   pfunc_tb_70_imag;
  wire       [31:0]   pfunc_tb_71_real;
  wire       [31:0]   pfunc_tb_71_imag;
  wire       [31:0]   pfunc_tb_72_real;
  wire       [31:0]   pfunc_tb_72_imag;
  wire       [31:0]   pfunc_tb_73_real;
  wire       [31:0]   pfunc_tb_73_imag;
  wire       [31:0]   pfunc_tb_74_real;
  wire       [31:0]   pfunc_tb_74_imag;
  wire       [31:0]   pfunc_tb_75_real;
  wire       [31:0]   pfunc_tb_75_imag;
  wire       [31:0]   pfunc_tb_76_real;
  wire       [31:0]   pfunc_tb_76_imag;
  wire       [31:0]   pfunc_tb_77_real;
  wire       [31:0]   pfunc_tb_77_imag;
  wire       [31:0]   pfunc_tb_78_real;
  wire       [31:0]   pfunc_tb_78_imag;
  wire       [31:0]   pfunc_tb_79_real;
  wire       [31:0]   pfunc_tb_79_imag;
  wire       [31:0]   pfunc_tb_80_real;
  wire       [31:0]   pfunc_tb_80_imag;
  wire       [31:0]   pfunc_tb_81_real;
  wire       [31:0]   pfunc_tb_81_imag;
  wire       [31:0]   pfunc_tb_82_real;
  wire       [31:0]   pfunc_tb_82_imag;
  wire       [31:0]   pfunc_tb_83_real;
  wire       [31:0]   pfunc_tb_83_imag;
  wire       [31:0]   pfunc_tb_84_real;
  wire       [31:0]   pfunc_tb_84_imag;
  wire       [31:0]   pfunc_tb_85_real;
  wire       [31:0]   pfunc_tb_85_imag;
  wire       [31:0]   pfunc_tb_86_real;
  wire       [31:0]   pfunc_tb_86_imag;
  wire       [31:0]   pfunc_tb_87_real;
  wire       [31:0]   pfunc_tb_87_imag;
  wire       [31:0]   pfunc_tb_88_real;
  wire       [31:0]   pfunc_tb_88_imag;
  wire       [31:0]   pfunc_tb_89_real;
  wire       [31:0]   pfunc_tb_89_imag;
  wire       [31:0]   pfunc_tb_90_real;
  wire       [31:0]   pfunc_tb_90_imag;
  wire       [31:0]   pfunc_tb_91_real;
  wire       [31:0]   pfunc_tb_91_imag;
  wire       [31:0]   pfunc_tb_92_real;
  wire       [31:0]   pfunc_tb_92_imag;
  wire       [31:0]   pfunc_tb_93_real;
  wire       [31:0]   pfunc_tb_93_imag;
  wire       [31:0]   pfunc_tb_94_real;
  wire       [31:0]   pfunc_tb_94_imag;
  wire       [31:0]   pfunc_tb_95_real;
  wire       [31:0]   pfunc_tb_95_imag;
  wire       [31:0]   pfunc_tb_96_real;
  wire       [31:0]   pfunc_tb_96_imag;
  wire       [31:0]   pfunc_tb_97_real;
  wire       [31:0]   pfunc_tb_97_imag;
  wire       [31:0]   pfunc_tb_98_real;
  wire       [31:0]   pfunc_tb_98_imag;
  wire       [31:0]   pfunc_tb_99_real;
  wire       [31:0]   pfunc_tb_99_imag;
  wire       [31:0]   pfunc_tb_100_real;
  wire       [31:0]   pfunc_tb_100_imag;
  wire       [31:0]   pfunc_tb_101_real;
  wire       [31:0]   pfunc_tb_101_imag;
  wire       [31:0]   pfunc_tb_102_real;
  wire       [31:0]   pfunc_tb_102_imag;
  wire       [31:0]   pfunc_tb_103_real;
  wire       [31:0]   pfunc_tb_103_imag;
  wire       [31:0]   pfunc_tb_104_real;
  wire       [31:0]   pfunc_tb_104_imag;
  wire       [31:0]   pfunc_tb_105_real;
  wire       [31:0]   pfunc_tb_105_imag;
  wire       [31:0]   pfunc_tb_106_real;
  wire       [31:0]   pfunc_tb_106_imag;
  wire       [31:0]   pfunc_tb_107_real;
  wire       [31:0]   pfunc_tb_107_imag;
  wire       [31:0]   pfunc_tb_108_real;
  wire       [31:0]   pfunc_tb_108_imag;
  wire       [31:0]   pfunc_tb_109_real;
  wire       [31:0]   pfunc_tb_109_imag;
  wire       [31:0]   pfunc_tb_110_real;
  wire       [31:0]   pfunc_tb_110_imag;
  wire       [31:0]   pfunc_tb_111_real;
  wire       [31:0]   pfunc_tb_111_imag;
  wire       [31:0]   pfunc_tb_112_real;
  wire       [31:0]   pfunc_tb_112_imag;
  wire       [31:0]   pfunc_tb_113_real;
  wire       [31:0]   pfunc_tb_113_imag;
  wire       [31:0]   pfunc_tb_114_real;
  wire       [31:0]   pfunc_tb_114_imag;
  wire       [31:0]   pfunc_tb_115_real;
  wire       [31:0]   pfunc_tb_115_imag;
  wire       [31:0]   pfunc_tb_116_real;
  wire       [31:0]   pfunc_tb_116_imag;
  wire       [31:0]   pfunc_tb_117_real;
  wire       [31:0]   pfunc_tb_117_imag;
  wire       [31:0]   pfunc_tb_118_real;
  wire       [31:0]   pfunc_tb_118_imag;
  wire       [31:0]   pfunc_tb_119_real;
  wire       [31:0]   pfunc_tb_119_imag;
  wire       [31:0]   pfunc_tb_120_real;
  wire       [31:0]   pfunc_tb_120_imag;
  wire       [31:0]   pfunc_tb_121_real;
  wire       [31:0]   pfunc_tb_121_imag;
  wire       [31:0]   pfunc_tb_122_real;
  wire       [31:0]   pfunc_tb_122_imag;
  wire       [31:0]   pfunc_tb_123_real;
  wire       [31:0]   pfunc_tb_123_imag;
  wire       [31:0]   pfunc_tb_124_real;
  wire       [31:0]   pfunc_tb_124_imag;
  wire       [31:0]   pfunc_tb_125_real;
  wire       [31:0]   pfunc_tb_125_imag;
  wire       [31:0]   pfunc_tb_126_real;
  wire       [31:0]   pfunc_tb_126_imag;
  wire       [31:0]   pfunc_tb_127_real;
  wire       [31:0]   pfunc_tb_127_imag;
  wire       [31:0]   pindx_tb_0;
  wire       [31:0]   pindx_tb_1;
  wire       [31:0]   pindx_tb_2;
  wire       [31:0]   pindx_tb_3;
  wire       [31:0]   pindx_tb_4;
  wire       [31:0]   pindx_tb_5;
  wire       [31:0]   pindx_tb_6;
  wire       [31:0]   pindx_tb_7;
  wire       [31:0]   pindx_tb_8;
  wire       [31:0]   pindx_tb_9;
  wire       [31:0]   pindx_tb_10;
  wire       [31:0]   pindx_tb_11;
  wire       [31:0]   pindx_tb_12;
  wire       [31:0]   pindx_tb_13;
  wire       [31:0]   pindx_tb_14;
  wire       [31:0]   pindx_tb_15;
  wire       [31:0]   pindx_tb_16;
  wire       [31:0]   pindx_tb_17;
  wire       [31:0]   pindx_tb_18;
  wire       [31:0]   pindx_tb_19;
  wire       [31:0]   pindx_tb_20;
  wire       [31:0]   pindx_tb_21;
  wire       [31:0]   pindx_tb_22;
  wire       [31:0]   pindx_tb_23;
  wire       [31:0]   pindx_tb_24;
  wire       [31:0]   pindx_tb_25;
  wire       [31:0]   pindx_tb_26;
  wire       [31:0]   pindx_tb_27;
  wire       [31:0]   pindx_tb_28;
  wire       [31:0]   pindx_tb_29;
  wire       [31:0]   pindx_tb_30;
  wire       [31:0]   pindx_tb_31;
  wire       [31:0]   pindx_tb_32;
  wire       [31:0]   pindx_tb_33;
  wire       [31:0]   pindx_tb_34;
  wire       [31:0]   pindx_tb_35;
  wire       [31:0]   pindx_tb_36;
  wire       [31:0]   pindx_tb_37;
  wire       [31:0]   pindx_tb_38;
  wire       [31:0]   pindx_tb_39;
  wire       [31:0]   pindx_tb_40;
  wire       [31:0]   pindx_tb_41;
  wire       [31:0]   pindx_tb_42;
  wire       [31:0]   pindx_tb_43;
  wire       [31:0]   pindx_tb_44;
  wire       [31:0]   pindx_tb_45;
  wire       [31:0]   pindx_tb_46;
  wire       [31:0]   pindx_tb_47;
  wire       [31:0]   pindx_tb_48;
  wire       [31:0]   pindx_tb_49;
  wire       [31:0]   pindx_tb_50;
  wire       [31:0]   pindx_tb_51;
  wire       [31:0]   pindx_tb_52;
  wire       [31:0]   pindx_tb_53;
  wire       [31:0]   pindx_tb_54;
  wire       [31:0]   pindx_tb_55;
  wire       [31:0]   pindx_tb_56;
  wire       [31:0]   pindx_tb_57;
  wire       [31:0]   pindx_tb_58;
  wire       [31:0]   pindx_tb_59;
  wire       [31:0]   pindx_tb_60;
  wire       [31:0]   pindx_tb_61;
  wire       [31:0]   pindx_tb_62;
  wire       [31:0]   pindx_tb_63;
  wire       [31:0]   pindx_tb_64;
  wire       [31:0]   pindx_tb_65;
  wire       [31:0]   pindx_tb_66;
  wire       [31:0]   pindx_tb_67;
  wire       [31:0]   pindx_tb_68;
  wire       [31:0]   pindx_tb_69;
  wire       [31:0]   pindx_tb_70;
  wire       [31:0]   pindx_tb_71;
  wire       [31:0]   pindx_tb_72;
  wire       [31:0]   pindx_tb_73;
  wire       [31:0]   pindx_tb_74;
  wire       [31:0]   pindx_tb_75;
  wire       [31:0]   pindx_tb_76;
  wire       [31:0]   pindx_tb_77;
  wire       [31:0]   pindx_tb_78;
  wire       [31:0]   pindx_tb_79;
  wire       [31:0]   pindx_tb_80;
  wire       [31:0]   pindx_tb_81;
  wire       [31:0]   pindx_tb_82;
  wire       [31:0]   pindx_tb_83;
  wire       [31:0]   pindx_tb_84;
  wire       [31:0]   pindx_tb_85;
  wire       [31:0]   pindx_tb_86;
  wire       [31:0]   pindx_tb_87;
  wire       [31:0]   pindx_tb_88;
  wire       [31:0]   pindx_tb_89;
  wire       [31:0]   pindx_tb_90;
  wire       [31:0]   pindx_tb_91;
  wire       [31:0]   pindx_tb_92;
  wire       [31:0]   pindx_tb_93;
  wire       [31:0]   pindx_tb_94;
  wire       [31:0]   pindx_tb_95;
  wire       [31:0]   pindx_tb_96;
  wire       [31:0]   pindx_tb_97;
  wire       [31:0]   pindx_tb_98;
  wire       [31:0]   pindx_tb_99;
  wire       [31:0]   pindx_tb_100;
  wire       [31:0]   pindx_tb_101;
  wire       [31:0]   pindx_tb_102;
  wire       [31:0]   pindx_tb_103;
  wire       [31:0]   pindx_tb_104;
  wire       [31:0]   pindx_tb_105;
  wire       [31:0]   pindx_tb_106;
  wire       [31:0]   pindx_tb_107;
  wire       [31:0]   pindx_tb_108;
  wire       [31:0]   pindx_tb_109;
  wire       [31:0]   pindx_tb_110;
  wire       [31:0]   pindx_tb_111;
  wire       [31:0]   pindx_tb_112;
  wire       [31:0]   pindx_tb_113;
  wire       [31:0]   pindx_tb_114;
  wire       [31:0]   pindx_tb_115;
  wire       [31:0]   pindx_tb_116;
  wire       [31:0]   pindx_tb_117;
  wire       [31:0]   pindx_tb_118;
  wire       [31:0]   pindx_tb_119;
  wire       [31:0]   pindx_tb_120;
  wire       [31:0]   pindx_tb_121;
  wire       [31:0]   pindx_tb_122;
  wire       [31:0]   pindx_tb_123;
  wire       [31:0]   pindx_tb_124;
  wire       [31:0]   pindx_tb_125;
  wire       [31:0]   pindx_tb_126;
  wire       [31:0]   pindx_tb_127;
  wire                idx_comp_0;
  wire                idx_comp_1;
  wire                idx_comp_2;
  wire                idx_comp_3;
  wire                idx_comp_4;
  wire                idx_comp_5;
  wire                idx_comp_6;
  wire                idx_comp_7;
  wire                idx_comp_8;
  wire                idx_comp_9;
  wire                idx_comp_10;
  wire                idx_comp_11;
  wire                idx_comp_12;
  wire                idx_comp_13;
  wire                idx_comp_14;
  wire                idx_comp_15;
  wire                idx_comp_16;
  wire                idx_comp_17;
  wire                idx_comp_18;
  wire                idx_comp_19;
  wire                idx_comp_20;
  wire                idx_comp_21;
  wire                idx_comp_22;
  wire                idx_comp_23;
  wire                idx_comp_24;
  wire                idx_comp_25;
  wire                idx_comp_26;
  wire                idx_comp_27;
  wire                idx_comp_28;
  wire                idx_comp_29;
  wire                idx_comp_30;
  wire                idx_comp_31;
  wire                idx_comp_32;
  wire                idx_comp_33;
  wire                idx_comp_34;
  wire                idx_comp_35;
  wire                idx_comp_36;
  wire                idx_comp_37;
  wire                idx_comp_38;
  wire                idx_comp_39;
  wire                idx_comp_40;
  wire                idx_comp_41;
  wire                idx_comp_42;
  wire                idx_comp_43;
  wire                idx_comp_44;
  wire                idx_comp_45;
  wire                idx_comp_46;
  wire                idx_comp_47;
  wire                idx_comp_48;
  wire                idx_comp_49;
  wire                idx_comp_50;
  wire                idx_comp_51;
  wire                idx_comp_52;
  wire                idx_comp_53;
  wire                idx_comp_54;
  wire                idx_comp_55;
  wire                idx_comp_56;
  wire                idx_comp_57;
  wire                idx_comp_58;
  wire                idx_comp_59;
  wire                idx_comp_60;
  wire                idx_comp_61;
  wire                idx_comp_62;
  wire                idx_comp_63;
  wire                idx_comp_64;
  wire                idx_comp_65;
  wire                idx_comp_66;
  wire                idx_comp_67;
  wire                idx_comp_68;
  wire                idx_comp_69;
  wire                idx_comp_70;
  wire                idx_comp_71;
  wire                idx_comp_72;
  wire                idx_comp_73;
  wire                idx_comp_74;
  wire                idx_comp_75;
  wire                idx_comp_76;
  wire                idx_comp_77;
  wire                idx_comp_78;
  wire                idx_comp_79;
  wire                idx_comp_80;
  wire                idx_comp_81;
  wire                idx_comp_82;
  wire                idx_comp_83;
  wire                idx_comp_84;
  wire                idx_comp_85;
  wire                idx_comp_86;
  wire                idx_comp_87;
  wire                idx_comp_88;
  wire                idx_comp_89;
  wire                idx_comp_90;
  wire                idx_comp_91;
  wire                idx_comp_92;
  wire                idx_comp_93;
  wire                idx_comp_94;
  wire                idx_comp_95;
  wire                idx_comp_96;
  wire                idx_comp_97;
  wire                idx_comp_98;
  wire                idx_comp_99;
  wire                idx_comp_100;
  wire                idx_comp_101;
  wire                idx_comp_102;
  wire                idx_comp_103;
  wire                idx_comp_104;
  wire                idx_comp_105;
  wire                idx_comp_106;
  wire                idx_comp_107;
  wire                idx_comp_108;
  wire                idx_comp_109;
  wire                idx_comp_110;
  wire                idx_comp_111;
  wire                idx_comp_112;
  wire                idx_comp_113;
  wire                idx_comp_114;
  wire                idx_comp_115;
  wire                idx_comp_116;
  wire                idx_comp_117;
  wire                idx_comp_118;
  wire                idx_comp_119;
  wire                idx_comp_120;
  wire                idx_comp_121;
  wire                idx_comp_122;
  wire                idx_comp_123;
  wire                idx_comp_124;
  wire                idx_comp_125;
  wire                idx_comp_126;
  wire                idx_comp_127;
  reg        [127:0]  idx_comp_vec;
  wire       [7:0]    ret;
  wire       [127:0]  x_tmp;
  wire       [7:0]    all_zeros;
  wire       [7:0]    all_left_zeros;
  wire       [7:0]    whole_bit_count;
  wire       [7:0]    left_bit_count;
  wire       [6:0]    ret_1;
  wire       [63:0]   x_tmp_1;
  wire       [6:0]    all_zeros_1;
  wire       [6:0]    all_left_zeros_1;
  wire       [6:0]    whole_bit_count_1;
  wire       [6:0]    left_bit_count_1;
  wire       [5:0]    ret_2;
  wire       [31:0]   x_tmp_2;
  wire       [5:0]    all_zeros_2;
  wire       [5:0]    all_left_zeros_2;
  wire       [5:0]    whole_bit_count_2;
  wire       [5:0]    left_bit_count_2;
  wire       [4:0]    ret_3;
  wire       [15:0]   x_tmp_3;
  wire       [4:0]    all_zeros_3;
  wire       [4:0]    all_left_zeros_3;
  wire       [4:0]    whole_bit_count_3;
  wire       [4:0]    left_bit_count_3;
  wire       [3:0]    ret_4;
  wire       [7:0]    x_tmp_4;
  wire       [3:0]    all_zeros_4;
  wire       [3:0]    all_left_zeros_4;
  wire       [3:0]    whole_bit_count_4;
  wire       [3:0]    left_bit_count_4;
  wire       [2:0]    ret_5;
  wire       [3:0]    x_tmp_5;
  wire       [2:0]    all_zeros_5;
  wire       [2:0]    all_left_zeros_5;
  wire       [2:0]    whole_bit_count_5;
  wire       [2:0]    left_bit_count_5;
  wire       [1:0]    ret_6;
  wire       [1:0]    x_tmp_6;
  wire       [1:0]    all_zeros_6;
  wire       [1:0]    all_left_zeros_6;
  wire       [1:0]    whole_bit_count_6;
  wire       [1:0]    left_bit_count_6;
  wire       [0:0]    _zz_2;
  wire       [0:0]    ret_7;
  wire       [0:0]    x_tmp_7;
  wire       [0:0]    all_zeros_7;
  wire       [0:0]    all_left_zeros_7;
  wire       [1:0]    left_all_zeros_count;
  wire       [0:0]    _zz_3;
  wire       [0:0]    ret_8;
  wire       [0:0]    x_tmp_8;
  wire       [0:0]    all_zeros_8;
  wire       [0:0]    all_left_zeros_8;
  wire       [1:0]    left_non_all_zeros_count;
  wire       [1:0]    non_zeros_lzc;
  wire       [2:0]    left_all_zeros_count_1;
  wire       [1:0]    ret_9;
  wire       [1:0]    x_tmp_9;
  wire       [1:0]    all_zeros_9;
  wire       [1:0]    all_left_zeros_9;
  wire       [1:0]    whole_bit_count_7;
  wire       [1:0]    left_bit_count_7;
  wire       [0:0]    _zz_4;
  wire       [0:0]    ret_10;
  wire       [0:0]    x_tmp_10;
  wire       [0:0]    all_zeros_10;
  wire       [0:0]    all_left_zeros_10;
  wire       [1:0]    left_all_zeros_count_2;
  wire       [0:0]    _zz_5;
  wire       [0:0]    ret_11;
  wire       [0:0]    x_tmp_11;
  wire       [0:0]    all_zeros_11;
  wire       [0:0]    all_left_zeros_11;
  wire       [1:0]    left_non_all_zeros_count_1;
  wire       [1:0]    non_zeros_lzc_1;
  wire       [2:0]    left_non_all_zeros_count_2;
  wire       [2:0]    non_zeros_lzc_2;
  wire       [3:0]    left_all_zeros_count_3;
  wire       [2:0]    ret_12;
  wire       [3:0]    x_tmp_12;
  wire       [2:0]    all_zeros_12;
  wire       [2:0]    all_left_zeros_12;
  wire       [2:0]    whole_bit_count_8;
  wire       [2:0]    left_bit_count_8;
  wire       [1:0]    ret_13;
  wire       [1:0]    x_tmp_13;
  wire       [1:0]    all_zeros_13;
  wire       [1:0]    all_left_zeros_13;
  wire       [1:0]    whole_bit_count_9;
  wire       [1:0]    left_bit_count_9;
  wire       [0:0]    _zz_6;
  wire       [0:0]    ret_14;
  wire       [0:0]    x_tmp_14;
  wire       [0:0]    all_zeros_14;
  wire       [0:0]    all_left_zeros_14;
  wire       [1:0]    left_all_zeros_count_4;
  wire       [0:0]    _zz_7;
  wire       [0:0]    ret_15;
  wire       [0:0]    x_tmp_15;
  wire       [0:0]    all_zeros_15;
  wire       [0:0]    all_left_zeros_15;
  wire       [1:0]    left_non_all_zeros_count_3;
  wire       [1:0]    non_zeros_lzc_3;
  wire       [2:0]    left_all_zeros_count_5;
  wire       [1:0]    ret_16;
  wire       [1:0]    x_tmp_16;
  wire       [1:0]    all_zeros_16;
  wire       [1:0]    all_left_zeros_16;
  wire       [1:0]    whole_bit_count_10;
  wire       [1:0]    left_bit_count_10;
  wire       [0:0]    _zz_8;
  wire       [0:0]    ret_17;
  wire       [0:0]    x_tmp_17;
  wire       [0:0]    all_zeros_17;
  wire       [0:0]    all_left_zeros_17;
  wire       [1:0]    left_all_zeros_count_6;
  wire       [0:0]    _zz_9;
  wire       [0:0]    ret_18;
  wire       [0:0]    x_tmp_18;
  wire       [0:0]    all_zeros_18;
  wire       [0:0]    all_left_zeros_18;
  wire       [1:0]    left_non_all_zeros_count_4;
  wire       [1:0]    non_zeros_lzc_4;
  wire       [2:0]    left_non_all_zeros_count_5;
  wire       [2:0]    non_zeros_lzc_5;
  wire       [3:0]    left_non_all_zeros_count_6;
  wire       [3:0]    non_zeros_lzc_6;
  wire       [4:0]    left_all_zeros_count_7;
  wire       [3:0]    ret_19;
  wire       [7:0]    x_tmp_19;
  wire       [3:0]    all_zeros_19;
  wire       [3:0]    all_left_zeros_19;
  wire       [3:0]    whole_bit_count_11;
  wire       [3:0]    left_bit_count_11;
  wire       [2:0]    ret_20;
  wire       [3:0]    x_tmp_20;
  wire       [2:0]    all_zeros_20;
  wire       [2:0]    all_left_zeros_20;
  wire       [2:0]    whole_bit_count_12;
  wire       [2:0]    left_bit_count_12;
  wire       [1:0]    ret_21;
  wire       [1:0]    x_tmp_21;
  wire       [1:0]    all_zeros_21;
  wire       [1:0]    all_left_zeros_21;
  wire       [1:0]    whole_bit_count_13;
  wire       [1:0]    left_bit_count_13;
  wire       [0:0]    _zz_10;
  wire       [0:0]    ret_22;
  wire       [0:0]    x_tmp_22;
  wire       [0:0]    all_zeros_22;
  wire       [0:0]    all_left_zeros_22;
  wire       [1:0]    left_all_zeros_count_8;
  wire       [0:0]    _zz_11;
  wire       [0:0]    ret_23;
  wire       [0:0]    x_tmp_23;
  wire       [0:0]    all_zeros_23;
  wire       [0:0]    all_left_zeros_23;
  wire       [1:0]    left_non_all_zeros_count_7;
  wire       [1:0]    non_zeros_lzc_7;
  wire       [2:0]    left_all_zeros_count_9;
  wire       [1:0]    ret_24;
  wire       [1:0]    x_tmp_24;
  wire       [1:0]    all_zeros_24;
  wire       [1:0]    all_left_zeros_24;
  wire       [1:0]    whole_bit_count_14;
  wire       [1:0]    left_bit_count_14;
  wire       [0:0]    _zz_12;
  wire       [0:0]    ret_25;
  wire       [0:0]    x_tmp_25;
  wire       [0:0]    all_zeros_25;
  wire       [0:0]    all_left_zeros_25;
  wire       [1:0]    left_all_zeros_count_10;
  wire       [0:0]    _zz_13;
  wire       [0:0]    ret_26;
  wire       [0:0]    x_tmp_26;
  wire       [0:0]    all_zeros_26;
  wire       [0:0]    all_left_zeros_26;
  wire       [1:0]    left_non_all_zeros_count_8;
  wire       [1:0]    non_zeros_lzc_8;
  wire       [2:0]    left_non_all_zeros_count_9;
  wire       [2:0]    non_zeros_lzc_9;
  wire       [3:0]    left_all_zeros_count_11;
  wire       [2:0]    ret_27;
  wire       [3:0]    x_tmp_27;
  wire       [2:0]    all_zeros_27;
  wire       [2:0]    all_left_zeros_27;
  wire       [2:0]    whole_bit_count_15;
  wire       [2:0]    left_bit_count_15;
  wire       [1:0]    ret_28;
  wire       [1:0]    x_tmp_28;
  wire       [1:0]    all_zeros_28;
  wire       [1:0]    all_left_zeros_28;
  wire       [1:0]    whole_bit_count_16;
  wire       [1:0]    left_bit_count_16;
  wire       [0:0]    _zz_14;
  wire       [0:0]    ret_29;
  wire       [0:0]    x_tmp_29;
  wire       [0:0]    all_zeros_29;
  wire       [0:0]    all_left_zeros_29;
  wire       [1:0]    left_all_zeros_count_12;
  wire       [0:0]    _zz_15;
  wire       [0:0]    ret_30;
  wire       [0:0]    x_tmp_30;
  wire       [0:0]    all_zeros_30;
  wire       [0:0]    all_left_zeros_30;
  wire       [1:0]    left_non_all_zeros_count_10;
  wire       [1:0]    non_zeros_lzc_10;
  wire       [2:0]    left_all_zeros_count_13;
  wire       [1:0]    ret_31;
  wire       [1:0]    x_tmp_31;
  wire       [1:0]    all_zeros_31;
  wire       [1:0]    all_left_zeros_31;
  wire       [1:0]    whole_bit_count_17;
  wire       [1:0]    left_bit_count_17;
  wire       [0:0]    _zz_16;
  wire       [0:0]    ret_32;
  wire       [0:0]    x_tmp_32;
  wire       [0:0]    all_zeros_32;
  wire       [0:0]    all_left_zeros_32;
  wire       [1:0]    left_all_zeros_count_14;
  wire       [0:0]    _zz_17;
  wire       [0:0]    ret_33;
  wire       [0:0]    x_tmp_33;
  wire       [0:0]    all_zeros_33;
  wire       [0:0]    all_left_zeros_33;
  wire       [1:0]    left_non_all_zeros_count_11;
  wire       [1:0]    non_zeros_lzc_11;
  wire       [2:0]    left_non_all_zeros_count_12;
  wire       [2:0]    non_zeros_lzc_12;
  wire       [3:0]    left_non_all_zeros_count_13;
  wire       [3:0]    non_zeros_lzc_13;
  wire       [4:0]    left_non_all_zeros_count_14;
  wire       [4:0]    non_zeros_lzc_14;
  wire       [5:0]    left_all_zeros_count_15;
  wire       [4:0]    ret_34;
  wire       [15:0]   x_tmp_34;
  wire       [4:0]    all_zeros_34;
  wire       [4:0]    all_left_zeros_34;
  wire       [4:0]    whole_bit_count_18;
  wire       [4:0]    left_bit_count_18;
  wire       [3:0]    ret_35;
  wire       [7:0]    x_tmp_35;
  wire       [3:0]    all_zeros_35;
  wire       [3:0]    all_left_zeros_35;
  wire       [3:0]    whole_bit_count_19;
  wire       [3:0]    left_bit_count_19;
  wire       [2:0]    ret_36;
  wire       [3:0]    x_tmp_36;
  wire       [2:0]    all_zeros_36;
  wire       [2:0]    all_left_zeros_36;
  wire       [2:0]    whole_bit_count_20;
  wire       [2:0]    left_bit_count_20;
  wire       [1:0]    ret_37;
  wire       [1:0]    x_tmp_37;
  wire       [1:0]    all_zeros_37;
  wire       [1:0]    all_left_zeros_37;
  wire       [1:0]    whole_bit_count_21;
  wire       [1:0]    left_bit_count_21;
  wire       [0:0]    _zz_18;
  wire       [0:0]    ret_38;
  wire       [0:0]    x_tmp_38;
  wire       [0:0]    all_zeros_38;
  wire       [0:0]    all_left_zeros_38;
  wire       [1:0]    left_all_zeros_count_16;
  wire       [0:0]    _zz_19;
  wire       [0:0]    ret_39;
  wire       [0:0]    x_tmp_39;
  wire       [0:0]    all_zeros_39;
  wire       [0:0]    all_left_zeros_39;
  wire       [1:0]    left_non_all_zeros_count_15;
  wire       [1:0]    non_zeros_lzc_15;
  wire       [2:0]    left_all_zeros_count_17;
  wire       [1:0]    ret_40;
  wire       [1:0]    x_tmp_40;
  wire       [1:0]    all_zeros_40;
  wire       [1:0]    all_left_zeros_40;
  wire       [1:0]    whole_bit_count_22;
  wire       [1:0]    left_bit_count_22;
  wire       [0:0]    _zz_20;
  wire       [0:0]    ret_41;
  wire       [0:0]    x_tmp_41;
  wire       [0:0]    all_zeros_41;
  wire       [0:0]    all_left_zeros_41;
  wire       [1:0]    left_all_zeros_count_18;
  wire       [0:0]    _zz_21;
  wire       [0:0]    ret_42;
  wire       [0:0]    x_tmp_42;
  wire       [0:0]    all_zeros_42;
  wire       [0:0]    all_left_zeros_42;
  wire       [1:0]    left_non_all_zeros_count_16;
  wire       [1:0]    non_zeros_lzc_16;
  wire       [2:0]    left_non_all_zeros_count_17;
  wire       [2:0]    non_zeros_lzc_17;
  wire       [3:0]    left_all_zeros_count_19;
  wire       [2:0]    ret_43;
  wire       [3:0]    x_tmp_43;
  wire       [2:0]    all_zeros_43;
  wire       [2:0]    all_left_zeros_43;
  wire       [2:0]    whole_bit_count_23;
  wire       [2:0]    left_bit_count_23;
  wire       [1:0]    ret_44;
  wire       [1:0]    x_tmp_44;
  wire       [1:0]    all_zeros_44;
  wire       [1:0]    all_left_zeros_44;
  wire       [1:0]    whole_bit_count_24;
  wire       [1:0]    left_bit_count_24;
  wire       [0:0]    _zz_22;
  wire       [0:0]    ret_45;
  wire       [0:0]    x_tmp_45;
  wire       [0:0]    all_zeros_45;
  wire       [0:0]    all_left_zeros_45;
  wire       [1:0]    left_all_zeros_count_20;
  wire       [0:0]    _zz_23;
  wire       [0:0]    ret_46;
  wire       [0:0]    x_tmp_46;
  wire       [0:0]    all_zeros_46;
  wire       [0:0]    all_left_zeros_46;
  wire       [1:0]    left_non_all_zeros_count_18;
  wire       [1:0]    non_zeros_lzc_18;
  wire       [2:0]    left_all_zeros_count_21;
  wire       [1:0]    ret_47;
  wire       [1:0]    x_tmp_47;
  wire       [1:0]    all_zeros_47;
  wire       [1:0]    all_left_zeros_47;
  wire       [1:0]    whole_bit_count_25;
  wire       [1:0]    left_bit_count_25;
  wire       [0:0]    _zz_24;
  wire       [0:0]    ret_48;
  wire       [0:0]    x_tmp_48;
  wire       [0:0]    all_zeros_48;
  wire       [0:0]    all_left_zeros_48;
  wire       [1:0]    left_all_zeros_count_22;
  wire       [0:0]    _zz_25;
  wire       [0:0]    ret_49;
  wire       [0:0]    x_tmp_49;
  wire       [0:0]    all_zeros_49;
  wire       [0:0]    all_left_zeros_49;
  wire       [1:0]    left_non_all_zeros_count_19;
  wire       [1:0]    non_zeros_lzc_19;
  wire       [2:0]    left_non_all_zeros_count_20;
  wire       [2:0]    non_zeros_lzc_20;
  wire       [3:0]    left_non_all_zeros_count_21;
  wire       [3:0]    non_zeros_lzc_21;
  wire       [4:0]    left_all_zeros_count_23;
  wire       [3:0]    ret_50;
  wire       [7:0]    x_tmp_50;
  wire       [3:0]    all_zeros_50;
  wire       [3:0]    all_left_zeros_50;
  wire       [3:0]    whole_bit_count_26;
  wire       [3:0]    left_bit_count_26;
  wire       [2:0]    ret_51;
  wire       [3:0]    x_tmp_51;
  wire       [2:0]    all_zeros_51;
  wire       [2:0]    all_left_zeros_51;
  wire       [2:0]    whole_bit_count_27;
  wire       [2:0]    left_bit_count_27;
  wire       [1:0]    ret_52;
  wire       [1:0]    x_tmp_52;
  wire       [1:0]    all_zeros_52;
  wire       [1:0]    all_left_zeros_52;
  wire       [1:0]    whole_bit_count_28;
  wire       [1:0]    left_bit_count_28;
  wire       [0:0]    _zz_26;
  wire       [0:0]    ret_53;
  wire       [0:0]    x_tmp_53;
  wire       [0:0]    all_zeros_53;
  wire       [0:0]    all_left_zeros_53;
  wire       [1:0]    left_all_zeros_count_24;
  wire       [0:0]    _zz_27;
  wire       [0:0]    ret_54;
  wire       [0:0]    x_tmp_54;
  wire       [0:0]    all_zeros_54;
  wire       [0:0]    all_left_zeros_54;
  wire       [1:0]    left_non_all_zeros_count_22;
  wire       [1:0]    non_zeros_lzc_22;
  wire       [2:0]    left_all_zeros_count_25;
  wire       [1:0]    ret_55;
  wire       [1:0]    x_tmp_55;
  wire       [1:0]    all_zeros_55;
  wire       [1:0]    all_left_zeros_55;
  wire       [1:0]    whole_bit_count_29;
  wire       [1:0]    left_bit_count_29;
  wire       [0:0]    _zz_28;
  wire       [0:0]    ret_56;
  wire       [0:0]    x_tmp_56;
  wire       [0:0]    all_zeros_56;
  wire       [0:0]    all_left_zeros_56;
  wire       [1:0]    left_all_zeros_count_26;
  wire       [0:0]    _zz_29;
  wire       [0:0]    ret_57;
  wire       [0:0]    x_tmp_57;
  wire       [0:0]    all_zeros_57;
  wire       [0:0]    all_left_zeros_57;
  wire       [1:0]    left_non_all_zeros_count_23;
  wire       [1:0]    non_zeros_lzc_23;
  wire       [2:0]    left_non_all_zeros_count_24;
  wire       [2:0]    non_zeros_lzc_24;
  wire       [3:0]    left_all_zeros_count_27;
  wire       [2:0]    ret_58;
  wire       [3:0]    x_tmp_58;
  wire       [2:0]    all_zeros_58;
  wire       [2:0]    all_left_zeros_58;
  wire       [2:0]    whole_bit_count_30;
  wire       [2:0]    left_bit_count_30;
  wire       [1:0]    ret_59;
  wire       [1:0]    x_tmp_59;
  wire       [1:0]    all_zeros_59;
  wire       [1:0]    all_left_zeros_59;
  wire       [1:0]    whole_bit_count_31;
  wire       [1:0]    left_bit_count_31;
  wire       [0:0]    _zz_30;
  wire       [0:0]    ret_60;
  wire       [0:0]    x_tmp_60;
  wire       [0:0]    all_zeros_60;
  wire       [0:0]    all_left_zeros_60;
  wire       [1:0]    left_all_zeros_count_28;
  wire       [0:0]    _zz_31;
  wire       [0:0]    ret_61;
  wire       [0:0]    x_tmp_61;
  wire       [0:0]    all_zeros_61;
  wire       [0:0]    all_left_zeros_61;
  wire       [1:0]    left_non_all_zeros_count_25;
  wire       [1:0]    non_zeros_lzc_25;
  wire       [2:0]    left_all_zeros_count_29;
  wire       [1:0]    ret_62;
  wire       [1:0]    x_tmp_62;
  wire       [1:0]    all_zeros_62;
  wire       [1:0]    all_left_zeros_62;
  wire       [1:0]    whole_bit_count_32;
  wire       [1:0]    left_bit_count_32;
  wire       [0:0]    _zz_32;
  wire       [0:0]    ret_63;
  wire       [0:0]    x_tmp_63;
  wire       [0:0]    all_zeros_63;
  wire       [0:0]    all_left_zeros_63;
  wire       [1:0]    left_all_zeros_count_30;
  wire       [0:0]    _zz_33;
  wire       [0:0]    ret_64;
  wire       [0:0]    x_tmp_64;
  wire       [0:0]    all_zeros_64;
  wire       [0:0]    all_left_zeros_64;
  wire       [1:0]    left_non_all_zeros_count_26;
  wire       [1:0]    non_zeros_lzc_26;
  wire       [2:0]    left_non_all_zeros_count_27;
  wire       [2:0]    non_zeros_lzc_27;
  wire       [3:0]    left_non_all_zeros_count_28;
  wire       [3:0]    non_zeros_lzc_28;
  wire       [4:0]    left_non_all_zeros_count_29;
  wire       [4:0]    non_zeros_lzc_29;
  wire       [5:0]    left_non_all_zeros_count_30;
  wire       [5:0]    non_zeros_lzc_30;
  wire       [6:0]    left_all_zeros_count_31;
  wire       [5:0]    ret_65;
  wire       [31:0]   x_tmp_65;
  wire       [5:0]    all_zeros_65;
  wire       [5:0]    all_left_zeros_65;
  wire       [5:0]    whole_bit_count_33;
  wire       [5:0]    left_bit_count_33;
  wire       [4:0]    ret_66;
  wire       [15:0]   x_tmp_66;
  wire       [4:0]    all_zeros_66;
  wire       [4:0]    all_left_zeros_66;
  wire       [4:0]    whole_bit_count_34;
  wire       [4:0]    left_bit_count_34;
  wire       [3:0]    ret_67;
  wire       [7:0]    x_tmp_67;
  wire       [3:0]    all_zeros_67;
  wire       [3:0]    all_left_zeros_67;
  wire       [3:0]    whole_bit_count_35;
  wire       [3:0]    left_bit_count_35;
  wire       [2:0]    ret_68;
  wire       [3:0]    x_tmp_68;
  wire       [2:0]    all_zeros_68;
  wire       [2:0]    all_left_zeros_68;
  wire       [2:0]    whole_bit_count_36;
  wire       [2:0]    left_bit_count_36;
  wire       [1:0]    ret_69;
  wire       [1:0]    x_tmp_69;
  wire       [1:0]    all_zeros_69;
  wire       [1:0]    all_left_zeros_69;
  wire       [1:0]    whole_bit_count_37;
  wire       [1:0]    left_bit_count_37;
  wire       [0:0]    _zz_34;
  wire       [0:0]    ret_70;
  wire       [0:0]    x_tmp_70;
  wire       [0:0]    all_zeros_70;
  wire       [0:0]    all_left_zeros_70;
  wire       [1:0]    left_all_zeros_count_32;
  wire       [0:0]    _zz_35;
  wire       [0:0]    ret_71;
  wire       [0:0]    x_tmp_71;
  wire       [0:0]    all_zeros_71;
  wire       [0:0]    all_left_zeros_71;
  wire       [1:0]    left_non_all_zeros_count_31;
  wire       [1:0]    non_zeros_lzc_31;
  wire       [2:0]    left_all_zeros_count_33;
  wire       [1:0]    ret_72;
  wire       [1:0]    x_tmp_72;
  wire       [1:0]    all_zeros_72;
  wire       [1:0]    all_left_zeros_72;
  wire       [1:0]    whole_bit_count_38;
  wire       [1:0]    left_bit_count_38;
  wire       [0:0]    _zz_36;
  wire       [0:0]    ret_73;
  wire       [0:0]    x_tmp_73;
  wire       [0:0]    all_zeros_73;
  wire       [0:0]    all_left_zeros_73;
  wire       [1:0]    left_all_zeros_count_34;
  wire       [0:0]    _zz_37;
  wire       [0:0]    ret_74;
  wire       [0:0]    x_tmp_74;
  wire       [0:0]    all_zeros_74;
  wire       [0:0]    all_left_zeros_74;
  wire       [1:0]    left_non_all_zeros_count_32;
  wire       [1:0]    non_zeros_lzc_32;
  wire       [2:0]    left_non_all_zeros_count_33;
  wire       [2:0]    non_zeros_lzc_33;
  wire       [3:0]    left_all_zeros_count_35;
  wire       [2:0]    ret_75;
  wire       [3:0]    x_tmp_75;
  wire       [2:0]    all_zeros_75;
  wire       [2:0]    all_left_zeros_75;
  wire       [2:0]    whole_bit_count_39;
  wire       [2:0]    left_bit_count_39;
  wire       [1:0]    ret_76;
  wire       [1:0]    x_tmp_76;
  wire       [1:0]    all_zeros_76;
  wire       [1:0]    all_left_zeros_76;
  wire       [1:0]    whole_bit_count_40;
  wire       [1:0]    left_bit_count_40;
  wire       [0:0]    _zz_38;
  wire       [0:0]    ret_77;
  wire       [0:0]    x_tmp_77;
  wire       [0:0]    all_zeros_77;
  wire       [0:0]    all_left_zeros_77;
  wire       [1:0]    left_all_zeros_count_36;
  wire       [0:0]    _zz_39;
  wire       [0:0]    ret_78;
  wire       [0:0]    x_tmp_78;
  wire       [0:0]    all_zeros_78;
  wire       [0:0]    all_left_zeros_78;
  wire       [1:0]    left_non_all_zeros_count_34;
  wire       [1:0]    non_zeros_lzc_34;
  wire       [2:0]    left_all_zeros_count_37;
  wire       [1:0]    ret_79;
  wire       [1:0]    x_tmp_79;
  wire       [1:0]    all_zeros_79;
  wire       [1:0]    all_left_zeros_79;
  wire       [1:0]    whole_bit_count_41;
  wire       [1:0]    left_bit_count_41;
  wire       [0:0]    _zz_40;
  wire       [0:0]    ret_80;
  wire       [0:0]    x_tmp_80;
  wire       [0:0]    all_zeros_80;
  wire       [0:0]    all_left_zeros_80;
  wire       [1:0]    left_all_zeros_count_38;
  wire       [0:0]    _zz_41;
  wire       [0:0]    ret_81;
  wire       [0:0]    x_tmp_81;
  wire       [0:0]    all_zeros_81;
  wire       [0:0]    all_left_zeros_81;
  wire       [1:0]    left_non_all_zeros_count_35;
  wire       [1:0]    non_zeros_lzc_35;
  wire       [2:0]    left_non_all_zeros_count_36;
  wire       [2:0]    non_zeros_lzc_36;
  wire       [3:0]    left_non_all_zeros_count_37;
  wire       [3:0]    non_zeros_lzc_37;
  wire       [4:0]    left_all_zeros_count_39;
  wire       [3:0]    ret_82;
  wire       [7:0]    x_tmp_82;
  wire       [3:0]    all_zeros_82;
  wire       [3:0]    all_left_zeros_82;
  wire       [3:0]    whole_bit_count_42;
  wire       [3:0]    left_bit_count_42;
  wire       [2:0]    ret_83;
  wire       [3:0]    x_tmp_83;
  wire       [2:0]    all_zeros_83;
  wire       [2:0]    all_left_zeros_83;
  wire       [2:0]    whole_bit_count_43;
  wire       [2:0]    left_bit_count_43;
  wire       [1:0]    ret_84;
  wire       [1:0]    x_tmp_84;
  wire       [1:0]    all_zeros_84;
  wire       [1:0]    all_left_zeros_84;
  wire       [1:0]    whole_bit_count_44;
  wire       [1:0]    left_bit_count_44;
  wire       [0:0]    _zz_42;
  wire       [0:0]    ret_85;
  wire       [0:0]    x_tmp_85;
  wire       [0:0]    all_zeros_85;
  wire       [0:0]    all_left_zeros_85;
  wire       [1:0]    left_all_zeros_count_40;
  wire       [0:0]    _zz_43;
  wire       [0:0]    ret_86;
  wire       [0:0]    x_tmp_86;
  wire       [0:0]    all_zeros_86;
  wire       [0:0]    all_left_zeros_86;
  wire       [1:0]    left_non_all_zeros_count_38;
  wire       [1:0]    non_zeros_lzc_38;
  wire       [2:0]    left_all_zeros_count_41;
  wire       [1:0]    ret_87;
  wire       [1:0]    x_tmp_87;
  wire       [1:0]    all_zeros_87;
  wire       [1:0]    all_left_zeros_87;
  wire       [1:0]    whole_bit_count_45;
  wire       [1:0]    left_bit_count_45;
  wire       [0:0]    _zz_44;
  wire       [0:0]    ret_88;
  wire       [0:0]    x_tmp_88;
  wire       [0:0]    all_zeros_88;
  wire       [0:0]    all_left_zeros_88;
  wire       [1:0]    left_all_zeros_count_42;
  wire       [0:0]    _zz_45;
  wire       [0:0]    ret_89;
  wire       [0:0]    x_tmp_89;
  wire       [0:0]    all_zeros_89;
  wire       [0:0]    all_left_zeros_89;
  wire       [1:0]    left_non_all_zeros_count_39;
  wire       [1:0]    non_zeros_lzc_39;
  wire       [2:0]    left_non_all_zeros_count_40;
  wire       [2:0]    non_zeros_lzc_40;
  wire       [3:0]    left_all_zeros_count_43;
  wire       [2:0]    ret_90;
  wire       [3:0]    x_tmp_90;
  wire       [2:0]    all_zeros_90;
  wire       [2:0]    all_left_zeros_90;
  wire       [2:0]    whole_bit_count_46;
  wire       [2:0]    left_bit_count_46;
  wire       [1:0]    ret_91;
  wire       [1:0]    x_tmp_91;
  wire       [1:0]    all_zeros_91;
  wire       [1:0]    all_left_zeros_91;
  wire       [1:0]    whole_bit_count_47;
  wire       [1:0]    left_bit_count_47;
  wire       [0:0]    _zz_46;
  wire       [0:0]    ret_92;
  wire       [0:0]    x_tmp_92;
  wire       [0:0]    all_zeros_92;
  wire       [0:0]    all_left_zeros_92;
  wire       [1:0]    left_all_zeros_count_44;
  wire       [0:0]    _zz_47;
  wire       [0:0]    ret_93;
  wire       [0:0]    x_tmp_93;
  wire       [0:0]    all_zeros_93;
  wire       [0:0]    all_left_zeros_93;
  wire       [1:0]    left_non_all_zeros_count_41;
  wire       [1:0]    non_zeros_lzc_41;
  wire       [2:0]    left_all_zeros_count_45;
  wire       [1:0]    ret_94;
  wire       [1:0]    x_tmp_94;
  wire       [1:0]    all_zeros_94;
  wire       [1:0]    all_left_zeros_94;
  wire       [1:0]    whole_bit_count_48;
  wire       [1:0]    left_bit_count_48;
  wire       [0:0]    _zz_48;
  wire       [0:0]    ret_95;
  wire       [0:0]    x_tmp_95;
  wire       [0:0]    all_zeros_95;
  wire       [0:0]    all_left_zeros_95;
  wire       [1:0]    left_all_zeros_count_46;
  wire       [0:0]    _zz_49;
  wire       [0:0]    ret_96;
  wire       [0:0]    x_tmp_96;
  wire       [0:0]    all_zeros_96;
  wire       [0:0]    all_left_zeros_96;
  wire       [1:0]    left_non_all_zeros_count_42;
  wire       [1:0]    non_zeros_lzc_42;
  wire       [2:0]    left_non_all_zeros_count_43;
  wire       [2:0]    non_zeros_lzc_43;
  wire       [3:0]    left_non_all_zeros_count_44;
  wire       [3:0]    non_zeros_lzc_44;
  wire       [4:0]    left_non_all_zeros_count_45;
  wire       [4:0]    non_zeros_lzc_45;
  wire       [5:0]    left_all_zeros_count_47;
  wire       [4:0]    ret_97;
  wire       [15:0]   x_tmp_97;
  wire       [4:0]    all_zeros_97;
  wire       [4:0]    all_left_zeros_97;
  wire       [4:0]    whole_bit_count_49;
  wire       [4:0]    left_bit_count_49;
  wire       [3:0]    ret_98;
  wire       [7:0]    x_tmp_98;
  wire       [3:0]    all_zeros_98;
  wire       [3:0]    all_left_zeros_98;
  wire       [3:0]    whole_bit_count_50;
  wire       [3:0]    left_bit_count_50;
  wire       [2:0]    ret_99;
  wire       [3:0]    x_tmp_99;
  wire       [2:0]    all_zeros_99;
  wire       [2:0]    all_left_zeros_99;
  wire       [2:0]    whole_bit_count_51;
  wire       [2:0]    left_bit_count_51;
  wire       [1:0]    ret_100;
  wire       [1:0]    x_tmp_100;
  wire       [1:0]    all_zeros_100;
  wire       [1:0]    all_left_zeros_100;
  wire       [1:0]    whole_bit_count_52;
  wire       [1:0]    left_bit_count_52;
  wire       [0:0]    _zz_50;
  wire       [0:0]    ret_101;
  wire       [0:0]    x_tmp_101;
  wire       [0:0]    all_zeros_101;
  wire       [0:0]    all_left_zeros_101;
  wire       [1:0]    left_all_zeros_count_48;
  wire       [0:0]    _zz_51;
  wire       [0:0]    ret_102;
  wire       [0:0]    x_tmp_102;
  wire       [0:0]    all_zeros_102;
  wire       [0:0]    all_left_zeros_102;
  wire       [1:0]    left_non_all_zeros_count_46;
  wire       [1:0]    non_zeros_lzc_46;
  wire       [2:0]    left_all_zeros_count_49;
  wire       [1:0]    ret_103;
  wire       [1:0]    x_tmp_103;
  wire       [1:0]    all_zeros_103;
  wire       [1:0]    all_left_zeros_103;
  wire       [1:0]    whole_bit_count_53;
  wire       [1:0]    left_bit_count_53;
  wire       [0:0]    _zz_52;
  wire       [0:0]    ret_104;
  wire       [0:0]    x_tmp_104;
  wire       [0:0]    all_zeros_104;
  wire       [0:0]    all_left_zeros_104;
  wire       [1:0]    left_all_zeros_count_50;
  wire       [0:0]    _zz_53;
  wire       [0:0]    ret_105;
  wire       [0:0]    x_tmp_105;
  wire       [0:0]    all_zeros_105;
  wire       [0:0]    all_left_zeros_105;
  wire       [1:0]    left_non_all_zeros_count_47;
  wire       [1:0]    non_zeros_lzc_47;
  wire       [2:0]    left_non_all_zeros_count_48;
  wire       [2:0]    non_zeros_lzc_48;
  wire       [3:0]    left_all_zeros_count_51;
  wire       [2:0]    ret_106;
  wire       [3:0]    x_tmp_106;
  wire       [2:0]    all_zeros_106;
  wire       [2:0]    all_left_zeros_106;
  wire       [2:0]    whole_bit_count_54;
  wire       [2:0]    left_bit_count_54;
  wire       [1:0]    ret_107;
  wire       [1:0]    x_tmp_107;
  wire       [1:0]    all_zeros_107;
  wire       [1:0]    all_left_zeros_107;
  wire       [1:0]    whole_bit_count_55;
  wire       [1:0]    left_bit_count_55;
  wire       [0:0]    _zz_54;
  wire       [0:0]    ret_108;
  wire       [0:0]    x_tmp_108;
  wire       [0:0]    all_zeros_108;
  wire       [0:0]    all_left_zeros_108;
  wire       [1:0]    left_all_zeros_count_52;
  wire       [0:0]    _zz_55;
  wire       [0:0]    ret_109;
  wire       [0:0]    x_tmp_109;
  wire       [0:0]    all_zeros_109;
  wire       [0:0]    all_left_zeros_109;
  wire       [1:0]    left_non_all_zeros_count_49;
  wire       [1:0]    non_zeros_lzc_49;
  wire       [2:0]    left_all_zeros_count_53;
  wire       [1:0]    ret_110;
  wire       [1:0]    x_tmp_110;
  wire       [1:0]    all_zeros_110;
  wire       [1:0]    all_left_zeros_110;
  wire       [1:0]    whole_bit_count_56;
  wire       [1:0]    left_bit_count_56;
  wire       [0:0]    _zz_56;
  wire       [0:0]    ret_111;
  wire       [0:0]    x_tmp_111;
  wire       [0:0]    all_zeros_111;
  wire       [0:0]    all_left_zeros_111;
  wire       [1:0]    left_all_zeros_count_54;
  wire       [0:0]    _zz_57;
  wire       [0:0]    ret_112;
  wire       [0:0]    x_tmp_112;
  wire       [0:0]    all_zeros_112;
  wire       [0:0]    all_left_zeros_112;
  wire       [1:0]    left_non_all_zeros_count_50;
  wire       [1:0]    non_zeros_lzc_50;
  wire       [2:0]    left_non_all_zeros_count_51;
  wire       [2:0]    non_zeros_lzc_51;
  wire       [3:0]    left_non_all_zeros_count_52;
  wire       [3:0]    non_zeros_lzc_52;
  wire       [4:0]    left_all_zeros_count_55;
  wire       [3:0]    ret_113;
  wire       [7:0]    x_tmp_113;
  wire       [3:0]    all_zeros_113;
  wire       [3:0]    all_left_zeros_113;
  wire       [3:0]    whole_bit_count_57;
  wire       [3:0]    left_bit_count_57;
  wire       [2:0]    ret_114;
  wire       [3:0]    x_tmp_114;
  wire       [2:0]    all_zeros_114;
  wire       [2:0]    all_left_zeros_114;
  wire       [2:0]    whole_bit_count_58;
  wire       [2:0]    left_bit_count_58;
  wire       [1:0]    ret_115;
  wire       [1:0]    x_tmp_115;
  wire       [1:0]    all_zeros_115;
  wire       [1:0]    all_left_zeros_115;
  wire       [1:0]    whole_bit_count_59;
  wire       [1:0]    left_bit_count_59;
  wire       [0:0]    _zz_58;
  wire       [0:0]    ret_116;
  wire       [0:0]    x_tmp_116;
  wire       [0:0]    all_zeros_116;
  wire       [0:0]    all_left_zeros_116;
  wire       [1:0]    left_all_zeros_count_56;
  wire       [0:0]    _zz_59;
  wire       [0:0]    ret_117;
  wire       [0:0]    x_tmp_117;
  wire       [0:0]    all_zeros_117;
  wire       [0:0]    all_left_zeros_117;
  wire       [1:0]    left_non_all_zeros_count_53;
  wire       [1:0]    non_zeros_lzc_53;
  wire       [2:0]    left_all_zeros_count_57;
  wire       [1:0]    ret_118;
  wire       [1:0]    x_tmp_118;
  wire       [1:0]    all_zeros_118;
  wire       [1:0]    all_left_zeros_118;
  wire       [1:0]    whole_bit_count_60;
  wire       [1:0]    left_bit_count_60;
  wire       [0:0]    _zz_60;
  wire       [0:0]    ret_119;
  wire       [0:0]    x_tmp_119;
  wire       [0:0]    all_zeros_119;
  wire       [0:0]    all_left_zeros_119;
  wire       [1:0]    left_all_zeros_count_58;
  wire       [0:0]    _zz_61;
  wire       [0:0]    ret_120;
  wire       [0:0]    x_tmp_120;
  wire       [0:0]    all_zeros_120;
  wire       [0:0]    all_left_zeros_120;
  wire       [1:0]    left_non_all_zeros_count_54;
  wire       [1:0]    non_zeros_lzc_54;
  wire       [2:0]    left_non_all_zeros_count_55;
  wire       [2:0]    non_zeros_lzc_55;
  wire       [3:0]    left_all_zeros_count_59;
  wire       [2:0]    ret_121;
  wire       [3:0]    x_tmp_121;
  wire       [2:0]    all_zeros_121;
  wire       [2:0]    all_left_zeros_121;
  wire       [2:0]    whole_bit_count_61;
  wire       [2:0]    left_bit_count_61;
  wire       [1:0]    ret_122;
  wire       [1:0]    x_tmp_122;
  wire       [1:0]    all_zeros_122;
  wire       [1:0]    all_left_zeros_122;
  wire       [1:0]    whole_bit_count_62;
  wire       [1:0]    left_bit_count_62;
  wire       [0:0]    _zz_62;
  wire       [0:0]    ret_123;
  wire       [0:0]    x_tmp_123;
  wire       [0:0]    all_zeros_123;
  wire       [0:0]    all_left_zeros_123;
  wire       [1:0]    left_all_zeros_count_60;
  wire       [0:0]    _zz_63;
  wire       [0:0]    ret_124;
  wire       [0:0]    x_tmp_124;
  wire       [0:0]    all_zeros_124;
  wire       [0:0]    all_left_zeros_124;
  wire       [1:0]    left_non_all_zeros_count_56;
  wire       [1:0]    non_zeros_lzc_56;
  wire       [2:0]    left_all_zeros_count_61;
  wire       [1:0]    ret_125;
  wire       [1:0]    x_tmp_125;
  wire       [1:0]    all_zeros_125;
  wire       [1:0]    all_left_zeros_125;
  wire       [1:0]    whole_bit_count_63;
  wire       [1:0]    left_bit_count_63;
  wire       [0:0]    _zz_64;
  wire       [0:0]    ret_126;
  wire       [0:0]    x_tmp_126;
  wire       [0:0]    all_zeros_126;
  wire       [0:0]    all_left_zeros_126;
  wire       [1:0]    left_all_zeros_count_62;
  wire       [0:0]    _zz_65;
  wire       [0:0]    ret_127;
  wire       [0:0]    x_tmp_127;
  wire       [0:0]    all_zeros_127;
  wire       [0:0]    all_left_zeros_127;
  wire       [1:0]    left_non_all_zeros_count_57;
  wire       [1:0]    non_zeros_lzc_57;
  wire       [2:0]    left_non_all_zeros_count_58;
  wire       [2:0]    non_zeros_lzc_58;
  wire       [3:0]    left_non_all_zeros_count_59;
  wire       [3:0]    non_zeros_lzc_59;
  wire       [4:0]    left_non_all_zeros_count_60;
  wire       [4:0]    non_zeros_lzc_60;
  wire       [5:0]    left_non_all_zeros_count_61;
  wire       [5:0]    non_zeros_lzc_61;
  wire       [6:0]    left_non_all_zeros_count_62;
  wire       [6:0]    non_zeros_lzc_62;
  wire       [7:0]    left_all_zeros_count_63;
  wire       [6:0]    ret_128;
  wire       [63:0]   x_tmp_128;
  wire       [6:0]    all_zeros_128;
  wire       [6:0]    all_left_zeros_128;
  wire       [6:0]    whole_bit_count_64;
  wire       [6:0]    left_bit_count_64;
  wire       [5:0]    ret_129;
  wire       [31:0]   x_tmp_129;
  wire       [5:0]    all_zeros_129;
  wire       [5:0]    all_left_zeros_129;
  wire       [5:0]    whole_bit_count_65;
  wire       [5:0]    left_bit_count_65;
  wire       [4:0]    ret_130;
  wire       [15:0]   x_tmp_130;
  wire       [4:0]    all_zeros_130;
  wire       [4:0]    all_left_zeros_130;
  wire       [4:0]    whole_bit_count_66;
  wire       [4:0]    left_bit_count_66;
  wire       [3:0]    ret_131;
  wire       [7:0]    x_tmp_131;
  wire       [3:0]    all_zeros_131;
  wire       [3:0]    all_left_zeros_131;
  wire       [3:0]    whole_bit_count_67;
  wire       [3:0]    left_bit_count_67;
  wire       [2:0]    ret_132;
  wire       [3:0]    x_tmp_132;
  wire       [2:0]    all_zeros_132;
  wire       [2:0]    all_left_zeros_132;
  wire       [2:0]    whole_bit_count_68;
  wire       [2:0]    left_bit_count_68;
  wire       [1:0]    ret_133;
  wire       [1:0]    x_tmp_133;
  wire       [1:0]    all_zeros_133;
  wire       [1:0]    all_left_zeros_133;
  wire       [1:0]    whole_bit_count_69;
  wire       [1:0]    left_bit_count_69;
  wire       [0:0]    _zz_66;
  wire       [0:0]    ret_134;
  wire       [0:0]    x_tmp_134;
  wire       [0:0]    all_zeros_134;
  wire       [0:0]    all_left_zeros_134;
  wire       [1:0]    left_all_zeros_count_64;
  wire       [0:0]    _zz_67;
  wire       [0:0]    ret_135;
  wire       [0:0]    x_tmp_135;
  wire       [0:0]    all_zeros_135;
  wire       [0:0]    all_left_zeros_135;
  wire       [1:0]    left_non_all_zeros_count_63;
  wire       [1:0]    non_zeros_lzc_63;
  wire       [2:0]    left_all_zeros_count_65;
  wire       [1:0]    ret_136;
  wire       [1:0]    x_tmp_136;
  wire       [1:0]    all_zeros_136;
  wire       [1:0]    all_left_zeros_136;
  wire       [1:0]    whole_bit_count_70;
  wire       [1:0]    left_bit_count_70;
  wire       [0:0]    _zz_68;
  wire       [0:0]    ret_137;
  wire       [0:0]    x_tmp_137;
  wire       [0:0]    all_zeros_137;
  wire       [0:0]    all_left_zeros_137;
  wire       [1:0]    left_all_zeros_count_66;
  wire       [0:0]    _zz_69;
  wire       [0:0]    ret_138;
  wire       [0:0]    x_tmp_138;
  wire       [0:0]    all_zeros_138;
  wire       [0:0]    all_left_zeros_138;
  wire       [1:0]    left_non_all_zeros_count_64;
  wire       [1:0]    non_zeros_lzc_64;
  wire       [2:0]    left_non_all_zeros_count_65;
  wire       [2:0]    non_zeros_lzc_65;
  wire       [3:0]    left_all_zeros_count_67;
  wire       [2:0]    ret_139;
  wire       [3:0]    x_tmp_139;
  wire       [2:0]    all_zeros_139;
  wire       [2:0]    all_left_zeros_139;
  wire       [2:0]    whole_bit_count_71;
  wire       [2:0]    left_bit_count_71;
  wire       [1:0]    ret_140;
  wire       [1:0]    x_tmp_140;
  wire       [1:0]    all_zeros_140;
  wire       [1:0]    all_left_zeros_140;
  wire       [1:0]    whole_bit_count_72;
  wire       [1:0]    left_bit_count_72;
  wire       [0:0]    _zz_70;
  wire       [0:0]    ret_141;
  wire       [0:0]    x_tmp_141;
  wire       [0:0]    all_zeros_141;
  wire       [0:0]    all_left_zeros_141;
  wire       [1:0]    left_all_zeros_count_68;
  wire       [0:0]    _zz_71;
  wire       [0:0]    ret_142;
  wire       [0:0]    x_tmp_142;
  wire       [0:0]    all_zeros_142;
  wire       [0:0]    all_left_zeros_142;
  wire       [1:0]    left_non_all_zeros_count_66;
  wire       [1:0]    non_zeros_lzc_66;
  wire       [2:0]    left_all_zeros_count_69;
  wire       [1:0]    ret_143;
  wire       [1:0]    x_tmp_143;
  wire       [1:0]    all_zeros_143;
  wire       [1:0]    all_left_zeros_143;
  wire       [1:0]    whole_bit_count_73;
  wire       [1:0]    left_bit_count_73;
  wire       [0:0]    _zz_72;
  wire       [0:0]    ret_144;
  wire       [0:0]    x_tmp_144;
  wire       [0:0]    all_zeros_144;
  wire       [0:0]    all_left_zeros_144;
  wire       [1:0]    left_all_zeros_count_70;
  wire       [0:0]    _zz_73;
  wire       [0:0]    ret_145;
  wire       [0:0]    x_tmp_145;
  wire       [0:0]    all_zeros_145;
  wire       [0:0]    all_left_zeros_145;
  wire       [1:0]    left_non_all_zeros_count_67;
  wire       [1:0]    non_zeros_lzc_67;
  wire       [2:0]    left_non_all_zeros_count_68;
  wire       [2:0]    non_zeros_lzc_68;
  wire       [3:0]    left_non_all_zeros_count_69;
  wire       [3:0]    non_zeros_lzc_69;
  wire       [4:0]    left_all_zeros_count_71;
  wire       [3:0]    ret_146;
  wire       [7:0]    x_tmp_146;
  wire       [3:0]    all_zeros_146;
  wire       [3:0]    all_left_zeros_146;
  wire       [3:0]    whole_bit_count_74;
  wire       [3:0]    left_bit_count_74;
  wire       [2:0]    ret_147;
  wire       [3:0]    x_tmp_147;
  wire       [2:0]    all_zeros_147;
  wire       [2:0]    all_left_zeros_147;
  wire       [2:0]    whole_bit_count_75;
  wire       [2:0]    left_bit_count_75;
  wire       [1:0]    ret_148;
  wire       [1:0]    x_tmp_148;
  wire       [1:0]    all_zeros_148;
  wire       [1:0]    all_left_zeros_148;
  wire       [1:0]    whole_bit_count_76;
  wire       [1:0]    left_bit_count_76;
  wire       [0:0]    _zz_74;
  wire       [0:0]    ret_149;
  wire       [0:0]    x_tmp_149;
  wire       [0:0]    all_zeros_149;
  wire       [0:0]    all_left_zeros_149;
  wire       [1:0]    left_all_zeros_count_72;
  wire       [0:0]    _zz_75;
  wire       [0:0]    ret_150;
  wire       [0:0]    x_tmp_150;
  wire       [0:0]    all_zeros_150;
  wire       [0:0]    all_left_zeros_150;
  wire       [1:0]    left_non_all_zeros_count_70;
  wire       [1:0]    non_zeros_lzc_70;
  wire       [2:0]    left_all_zeros_count_73;
  wire       [1:0]    ret_151;
  wire       [1:0]    x_tmp_151;
  wire       [1:0]    all_zeros_151;
  wire       [1:0]    all_left_zeros_151;
  wire       [1:0]    whole_bit_count_77;
  wire       [1:0]    left_bit_count_77;
  wire       [0:0]    _zz_76;
  wire       [0:0]    ret_152;
  wire       [0:0]    x_tmp_152;
  wire       [0:0]    all_zeros_152;
  wire       [0:0]    all_left_zeros_152;
  wire       [1:0]    left_all_zeros_count_74;
  wire       [0:0]    _zz_77;
  wire       [0:0]    ret_153;
  wire       [0:0]    x_tmp_153;
  wire       [0:0]    all_zeros_153;
  wire       [0:0]    all_left_zeros_153;
  wire       [1:0]    left_non_all_zeros_count_71;
  wire       [1:0]    non_zeros_lzc_71;
  wire       [2:0]    left_non_all_zeros_count_72;
  wire       [2:0]    non_zeros_lzc_72;
  wire       [3:0]    left_all_zeros_count_75;
  wire       [2:0]    ret_154;
  wire       [3:0]    x_tmp_154;
  wire       [2:0]    all_zeros_154;
  wire       [2:0]    all_left_zeros_154;
  wire       [2:0]    whole_bit_count_78;
  wire       [2:0]    left_bit_count_78;
  wire       [1:0]    ret_155;
  wire       [1:0]    x_tmp_155;
  wire       [1:0]    all_zeros_155;
  wire       [1:0]    all_left_zeros_155;
  wire       [1:0]    whole_bit_count_79;
  wire       [1:0]    left_bit_count_79;
  wire       [0:0]    _zz_78;
  wire       [0:0]    ret_156;
  wire       [0:0]    x_tmp_156;
  wire       [0:0]    all_zeros_156;
  wire       [0:0]    all_left_zeros_156;
  wire       [1:0]    left_all_zeros_count_76;
  wire       [0:0]    _zz_79;
  wire       [0:0]    ret_157;
  wire       [0:0]    x_tmp_157;
  wire       [0:0]    all_zeros_157;
  wire       [0:0]    all_left_zeros_157;
  wire       [1:0]    left_non_all_zeros_count_73;
  wire       [1:0]    non_zeros_lzc_73;
  wire       [2:0]    left_all_zeros_count_77;
  wire       [1:0]    ret_158;
  wire       [1:0]    x_tmp_158;
  wire       [1:0]    all_zeros_158;
  wire       [1:0]    all_left_zeros_158;
  wire       [1:0]    whole_bit_count_80;
  wire       [1:0]    left_bit_count_80;
  wire       [0:0]    _zz_80;
  wire       [0:0]    ret_159;
  wire       [0:0]    x_tmp_159;
  wire       [0:0]    all_zeros_159;
  wire       [0:0]    all_left_zeros_159;
  wire       [1:0]    left_all_zeros_count_78;
  wire       [0:0]    _zz_81;
  wire       [0:0]    ret_160;
  wire       [0:0]    x_tmp_160;
  wire       [0:0]    all_zeros_160;
  wire       [0:0]    all_left_zeros_160;
  wire       [1:0]    left_non_all_zeros_count_74;
  wire       [1:0]    non_zeros_lzc_74;
  wire       [2:0]    left_non_all_zeros_count_75;
  wire       [2:0]    non_zeros_lzc_75;
  wire       [3:0]    left_non_all_zeros_count_76;
  wire       [3:0]    non_zeros_lzc_76;
  wire       [4:0]    left_non_all_zeros_count_77;
  wire       [4:0]    non_zeros_lzc_77;
  wire       [5:0]    left_all_zeros_count_79;
  wire       [4:0]    ret_161;
  wire       [15:0]   x_tmp_161;
  wire       [4:0]    all_zeros_161;
  wire       [4:0]    all_left_zeros_161;
  wire       [4:0]    whole_bit_count_81;
  wire       [4:0]    left_bit_count_81;
  wire       [3:0]    ret_162;
  wire       [7:0]    x_tmp_162;
  wire       [3:0]    all_zeros_162;
  wire       [3:0]    all_left_zeros_162;
  wire       [3:0]    whole_bit_count_82;
  wire       [3:0]    left_bit_count_82;
  wire       [2:0]    ret_163;
  wire       [3:0]    x_tmp_163;
  wire       [2:0]    all_zeros_163;
  wire       [2:0]    all_left_zeros_163;
  wire       [2:0]    whole_bit_count_83;
  wire       [2:0]    left_bit_count_83;
  wire       [1:0]    ret_164;
  wire       [1:0]    x_tmp_164;
  wire       [1:0]    all_zeros_164;
  wire       [1:0]    all_left_zeros_164;
  wire       [1:0]    whole_bit_count_84;
  wire       [1:0]    left_bit_count_84;
  wire       [0:0]    _zz_82;
  wire       [0:0]    ret_165;
  wire       [0:0]    x_tmp_165;
  wire       [0:0]    all_zeros_165;
  wire       [0:0]    all_left_zeros_165;
  wire       [1:0]    left_all_zeros_count_80;
  wire       [0:0]    _zz_83;
  wire       [0:0]    ret_166;
  wire       [0:0]    x_tmp_166;
  wire       [0:0]    all_zeros_166;
  wire       [0:0]    all_left_zeros_166;
  wire       [1:0]    left_non_all_zeros_count_78;
  wire       [1:0]    non_zeros_lzc_78;
  wire       [2:0]    left_all_zeros_count_81;
  wire       [1:0]    ret_167;
  wire       [1:0]    x_tmp_167;
  wire       [1:0]    all_zeros_167;
  wire       [1:0]    all_left_zeros_167;
  wire       [1:0]    whole_bit_count_85;
  wire       [1:0]    left_bit_count_85;
  wire       [0:0]    _zz_84;
  wire       [0:0]    ret_168;
  wire       [0:0]    x_tmp_168;
  wire       [0:0]    all_zeros_168;
  wire       [0:0]    all_left_zeros_168;
  wire       [1:0]    left_all_zeros_count_82;
  wire       [0:0]    _zz_85;
  wire       [0:0]    ret_169;
  wire       [0:0]    x_tmp_169;
  wire       [0:0]    all_zeros_169;
  wire       [0:0]    all_left_zeros_169;
  wire       [1:0]    left_non_all_zeros_count_79;
  wire       [1:0]    non_zeros_lzc_79;
  wire       [2:0]    left_non_all_zeros_count_80;
  wire       [2:0]    non_zeros_lzc_80;
  wire       [3:0]    left_all_zeros_count_83;
  wire       [2:0]    ret_170;
  wire       [3:0]    x_tmp_170;
  wire       [2:0]    all_zeros_170;
  wire       [2:0]    all_left_zeros_170;
  wire       [2:0]    whole_bit_count_86;
  wire       [2:0]    left_bit_count_86;
  wire       [1:0]    ret_171;
  wire       [1:0]    x_tmp_171;
  wire       [1:0]    all_zeros_171;
  wire       [1:0]    all_left_zeros_171;
  wire       [1:0]    whole_bit_count_87;
  wire       [1:0]    left_bit_count_87;
  wire       [0:0]    _zz_86;
  wire       [0:0]    ret_172;
  wire       [0:0]    x_tmp_172;
  wire       [0:0]    all_zeros_172;
  wire       [0:0]    all_left_zeros_172;
  wire       [1:0]    left_all_zeros_count_84;
  wire       [0:0]    _zz_87;
  wire       [0:0]    ret_173;
  wire       [0:0]    x_tmp_173;
  wire       [0:0]    all_zeros_173;
  wire       [0:0]    all_left_zeros_173;
  wire       [1:0]    left_non_all_zeros_count_81;
  wire       [1:0]    non_zeros_lzc_81;
  wire       [2:0]    left_all_zeros_count_85;
  wire       [1:0]    ret_174;
  wire       [1:0]    x_tmp_174;
  wire       [1:0]    all_zeros_174;
  wire       [1:0]    all_left_zeros_174;
  wire       [1:0]    whole_bit_count_88;
  wire       [1:0]    left_bit_count_88;
  wire       [0:0]    _zz_88;
  wire       [0:0]    ret_175;
  wire       [0:0]    x_tmp_175;
  wire       [0:0]    all_zeros_175;
  wire       [0:0]    all_left_zeros_175;
  wire       [1:0]    left_all_zeros_count_86;
  wire       [0:0]    _zz_89;
  wire       [0:0]    ret_176;
  wire       [0:0]    x_tmp_176;
  wire       [0:0]    all_zeros_176;
  wire       [0:0]    all_left_zeros_176;
  wire       [1:0]    left_non_all_zeros_count_82;
  wire       [1:0]    non_zeros_lzc_82;
  wire       [2:0]    left_non_all_zeros_count_83;
  wire       [2:0]    non_zeros_lzc_83;
  wire       [3:0]    left_non_all_zeros_count_84;
  wire       [3:0]    non_zeros_lzc_84;
  wire       [4:0]    left_all_zeros_count_87;
  wire       [3:0]    ret_177;
  wire       [7:0]    x_tmp_177;
  wire       [3:0]    all_zeros_177;
  wire       [3:0]    all_left_zeros_177;
  wire       [3:0]    whole_bit_count_89;
  wire       [3:0]    left_bit_count_89;
  wire       [2:0]    ret_178;
  wire       [3:0]    x_tmp_178;
  wire       [2:0]    all_zeros_178;
  wire       [2:0]    all_left_zeros_178;
  wire       [2:0]    whole_bit_count_90;
  wire       [2:0]    left_bit_count_90;
  wire       [1:0]    ret_179;
  wire       [1:0]    x_tmp_179;
  wire       [1:0]    all_zeros_179;
  wire       [1:0]    all_left_zeros_179;
  wire       [1:0]    whole_bit_count_91;
  wire       [1:0]    left_bit_count_91;
  wire       [0:0]    _zz_90;
  wire       [0:0]    ret_180;
  wire       [0:0]    x_tmp_180;
  wire       [0:0]    all_zeros_180;
  wire       [0:0]    all_left_zeros_180;
  wire       [1:0]    left_all_zeros_count_88;
  wire       [0:0]    _zz_91;
  wire       [0:0]    ret_181;
  wire       [0:0]    x_tmp_181;
  wire       [0:0]    all_zeros_181;
  wire       [0:0]    all_left_zeros_181;
  wire       [1:0]    left_non_all_zeros_count_85;
  wire       [1:0]    non_zeros_lzc_85;
  wire       [2:0]    left_all_zeros_count_89;
  wire       [1:0]    ret_182;
  wire       [1:0]    x_tmp_182;
  wire       [1:0]    all_zeros_182;
  wire       [1:0]    all_left_zeros_182;
  wire       [1:0]    whole_bit_count_92;
  wire       [1:0]    left_bit_count_92;
  wire       [0:0]    _zz_92;
  wire       [0:0]    ret_183;
  wire       [0:0]    x_tmp_183;
  wire       [0:0]    all_zeros_183;
  wire       [0:0]    all_left_zeros_183;
  wire       [1:0]    left_all_zeros_count_90;
  wire       [0:0]    _zz_93;
  wire       [0:0]    ret_184;
  wire       [0:0]    x_tmp_184;
  wire       [0:0]    all_zeros_184;
  wire       [0:0]    all_left_zeros_184;
  wire       [1:0]    left_non_all_zeros_count_86;
  wire       [1:0]    non_zeros_lzc_86;
  wire       [2:0]    left_non_all_zeros_count_87;
  wire       [2:0]    non_zeros_lzc_87;
  wire       [3:0]    left_all_zeros_count_91;
  wire       [2:0]    ret_185;
  wire       [3:0]    x_tmp_185;
  wire       [2:0]    all_zeros_185;
  wire       [2:0]    all_left_zeros_185;
  wire       [2:0]    whole_bit_count_93;
  wire       [2:0]    left_bit_count_93;
  wire       [1:0]    ret_186;
  wire       [1:0]    x_tmp_186;
  wire       [1:0]    all_zeros_186;
  wire       [1:0]    all_left_zeros_186;
  wire       [1:0]    whole_bit_count_94;
  wire       [1:0]    left_bit_count_94;
  wire       [0:0]    _zz_94;
  wire       [0:0]    ret_187;
  wire       [0:0]    x_tmp_187;
  wire       [0:0]    all_zeros_187;
  wire       [0:0]    all_left_zeros_187;
  wire       [1:0]    left_all_zeros_count_92;
  wire       [0:0]    _zz_95;
  wire       [0:0]    ret_188;
  wire       [0:0]    x_tmp_188;
  wire       [0:0]    all_zeros_188;
  wire       [0:0]    all_left_zeros_188;
  wire       [1:0]    left_non_all_zeros_count_88;
  wire       [1:0]    non_zeros_lzc_88;
  wire       [2:0]    left_all_zeros_count_93;
  wire       [1:0]    ret_189;
  wire       [1:0]    x_tmp_189;
  wire       [1:0]    all_zeros_189;
  wire       [1:0]    all_left_zeros_189;
  wire       [1:0]    whole_bit_count_95;
  wire       [1:0]    left_bit_count_95;
  wire       [0:0]    _zz_96;
  wire       [0:0]    ret_190;
  wire       [0:0]    x_tmp_190;
  wire       [0:0]    all_zeros_190;
  wire       [0:0]    all_left_zeros_190;
  wire       [1:0]    left_all_zeros_count_94;
  wire       [0:0]    _zz_97;
  wire       [0:0]    ret_191;
  wire       [0:0]    x_tmp_191;
  wire       [0:0]    all_zeros_191;
  wire       [0:0]    all_left_zeros_191;
  wire       [1:0]    left_non_all_zeros_count_89;
  wire       [1:0]    non_zeros_lzc_89;
  wire       [2:0]    left_non_all_zeros_count_90;
  wire       [2:0]    non_zeros_lzc_90;
  wire       [3:0]    left_non_all_zeros_count_91;
  wire       [3:0]    non_zeros_lzc_91;
  wire       [4:0]    left_non_all_zeros_count_92;
  wire       [4:0]    non_zeros_lzc_92;
  wire       [5:0]    left_non_all_zeros_count_93;
  wire       [5:0]    non_zeros_lzc_93;
  wire       [6:0]    left_all_zeros_count_95;
  wire       [5:0]    ret_192;
  wire       [31:0]   x_tmp_192;
  wire       [5:0]    all_zeros_192;
  wire       [5:0]    all_left_zeros_192;
  wire       [5:0]    whole_bit_count_96;
  wire       [5:0]    left_bit_count_96;
  wire       [4:0]    ret_193;
  wire       [15:0]   x_tmp_193;
  wire       [4:0]    all_zeros_193;
  wire       [4:0]    all_left_zeros_193;
  wire       [4:0]    whole_bit_count_97;
  wire       [4:0]    left_bit_count_97;
  wire       [3:0]    ret_194;
  wire       [7:0]    x_tmp_194;
  wire       [3:0]    all_zeros_194;
  wire       [3:0]    all_left_zeros_194;
  wire       [3:0]    whole_bit_count_98;
  wire       [3:0]    left_bit_count_98;
  wire       [2:0]    ret_195;
  wire       [3:0]    x_tmp_195;
  wire       [2:0]    all_zeros_195;
  wire       [2:0]    all_left_zeros_195;
  wire       [2:0]    whole_bit_count_99;
  wire       [2:0]    left_bit_count_99;
  wire       [1:0]    ret_196;
  wire       [1:0]    x_tmp_196;
  wire       [1:0]    all_zeros_196;
  wire       [1:0]    all_left_zeros_196;
  wire       [1:0]    whole_bit_count_100;
  wire       [1:0]    left_bit_count_100;
  wire       [0:0]    _zz_98;
  wire       [0:0]    ret_197;
  wire       [0:0]    x_tmp_197;
  wire       [0:0]    all_zeros_197;
  wire       [0:0]    all_left_zeros_197;
  wire       [1:0]    left_all_zeros_count_96;
  wire       [0:0]    _zz_99;
  wire       [0:0]    ret_198;
  wire       [0:0]    x_tmp_198;
  wire       [0:0]    all_zeros_198;
  wire       [0:0]    all_left_zeros_198;
  wire       [1:0]    left_non_all_zeros_count_94;
  wire       [1:0]    non_zeros_lzc_94;
  wire       [2:0]    left_all_zeros_count_97;
  wire       [1:0]    ret_199;
  wire       [1:0]    x_tmp_199;
  wire       [1:0]    all_zeros_199;
  wire       [1:0]    all_left_zeros_199;
  wire       [1:0]    whole_bit_count_101;
  wire       [1:0]    left_bit_count_101;
  wire       [0:0]    _zz_100;
  wire       [0:0]    ret_200;
  wire       [0:0]    x_tmp_200;
  wire       [0:0]    all_zeros_200;
  wire       [0:0]    all_left_zeros_200;
  wire       [1:0]    left_all_zeros_count_98;
  wire       [0:0]    _zz_101;
  wire       [0:0]    ret_201;
  wire       [0:0]    x_tmp_201;
  wire       [0:0]    all_zeros_201;
  wire       [0:0]    all_left_zeros_201;
  wire       [1:0]    left_non_all_zeros_count_95;
  wire       [1:0]    non_zeros_lzc_95;
  wire       [2:0]    left_non_all_zeros_count_96;
  wire       [2:0]    non_zeros_lzc_96;
  wire       [3:0]    left_all_zeros_count_99;
  wire       [2:0]    ret_202;
  wire       [3:0]    x_tmp_202;
  wire       [2:0]    all_zeros_202;
  wire       [2:0]    all_left_zeros_202;
  wire       [2:0]    whole_bit_count_102;
  wire       [2:0]    left_bit_count_102;
  wire       [1:0]    ret_203;
  wire       [1:0]    x_tmp_203;
  wire       [1:0]    all_zeros_203;
  wire       [1:0]    all_left_zeros_203;
  wire       [1:0]    whole_bit_count_103;
  wire       [1:0]    left_bit_count_103;
  wire       [0:0]    _zz_102;
  wire       [0:0]    ret_204;
  wire       [0:0]    x_tmp_204;
  wire       [0:0]    all_zeros_204;
  wire       [0:0]    all_left_zeros_204;
  wire       [1:0]    left_all_zeros_count_100;
  wire       [0:0]    _zz_103;
  wire       [0:0]    ret_205;
  wire       [0:0]    x_tmp_205;
  wire       [0:0]    all_zeros_205;
  wire       [0:0]    all_left_zeros_205;
  wire       [1:0]    left_non_all_zeros_count_97;
  wire       [1:0]    non_zeros_lzc_97;
  wire       [2:0]    left_all_zeros_count_101;
  wire       [1:0]    ret_206;
  wire       [1:0]    x_tmp_206;
  wire       [1:0]    all_zeros_206;
  wire       [1:0]    all_left_zeros_206;
  wire       [1:0]    whole_bit_count_104;
  wire       [1:0]    left_bit_count_104;
  wire       [0:0]    _zz_104;
  wire       [0:0]    ret_207;
  wire       [0:0]    x_tmp_207;
  wire       [0:0]    all_zeros_207;
  wire       [0:0]    all_left_zeros_207;
  wire       [1:0]    left_all_zeros_count_102;
  wire       [0:0]    _zz_105;
  wire       [0:0]    ret_208;
  wire       [0:0]    x_tmp_208;
  wire       [0:0]    all_zeros_208;
  wire       [0:0]    all_left_zeros_208;
  wire       [1:0]    left_non_all_zeros_count_98;
  wire       [1:0]    non_zeros_lzc_98;
  wire       [2:0]    left_non_all_zeros_count_99;
  wire       [2:0]    non_zeros_lzc_99;
  wire       [3:0]    left_non_all_zeros_count_100;
  wire       [3:0]    non_zeros_lzc_100;
  wire       [4:0]    left_all_zeros_count_103;
  wire       [3:0]    ret_209;
  wire       [7:0]    x_tmp_209;
  wire       [3:0]    all_zeros_209;
  wire       [3:0]    all_left_zeros_209;
  wire       [3:0]    whole_bit_count_105;
  wire       [3:0]    left_bit_count_105;
  wire       [2:0]    ret_210;
  wire       [3:0]    x_tmp_210;
  wire       [2:0]    all_zeros_210;
  wire       [2:0]    all_left_zeros_210;
  wire       [2:0]    whole_bit_count_106;
  wire       [2:0]    left_bit_count_106;
  wire       [1:0]    ret_211;
  wire       [1:0]    x_tmp_211;
  wire       [1:0]    all_zeros_211;
  wire       [1:0]    all_left_zeros_211;
  wire       [1:0]    whole_bit_count_107;
  wire       [1:0]    left_bit_count_107;
  wire       [0:0]    _zz_106;
  wire       [0:0]    ret_212;
  wire       [0:0]    x_tmp_212;
  wire       [0:0]    all_zeros_212;
  wire       [0:0]    all_left_zeros_212;
  wire       [1:0]    left_all_zeros_count_104;
  wire       [0:0]    _zz_107;
  wire       [0:0]    ret_213;
  wire       [0:0]    x_tmp_213;
  wire       [0:0]    all_zeros_213;
  wire       [0:0]    all_left_zeros_213;
  wire       [1:0]    left_non_all_zeros_count_101;
  wire       [1:0]    non_zeros_lzc_101;
  wire       [2:0]    left_all_zeros_count_105;
  wire       [1:0]    ret_214;
  wire       [1:0]    x_tmp_214;
  wire       [1:0]    all_zeros_214;
  wire       [1:0]    all_left_zeros_214;
  wire       [1:0]    whole_bit_count_108;
  wire       [1:0]    left_bit_count_108;
  wire       [0:0]    _zz_108;
  wire       [0:0]    ret_215;
  wire       [0:0]    x_tmp_215;
  wire       [0:0]    all_zeros_215;
  wire       [0:0]    all_left_zeros_215;
  wire       [1:0]    left_all_zeros_count_106;
  wire       [0:0]    _zz_109;
  wire       [0:0]    ret_216;
  wire       [0:0]    x_tmp_216;
  wire       [0:0]    all_zeros_216;
  wire       [0:0]    all_left_zeros_216;
  wire       [1:0]    left_non_all_zeros_count_102;
  wire       [1:0]    non_zeros_lzc_102;
  wire       [2:0]    left_non_all_zeros_count_103;
  wire       [2:0]    non_zeros_lzc_103;
  wire       [3:0]    left_all_zeros_count_107;
  wire       [2:0]    ret_217;
  wire       [3:0]    x_tmp_217;
  wire       [2:0]    all_zeros_217;
  wire       [2:0]    all_left_zeros_217;
  wire       [2:0]    whole_bit_count_109;
  wire       [2:0]    left_bit_count_109;
  wire       [1:0]    ret_218;
  wire       [1:0]    x_tmp_218;
  wire       [1:0]    all_zeros_218;
  wire       [1:0]    all_left_zeros_218;
  wire       [1:0]    whole_bit_count_110;
  wire       [1:0]    left_bit_count_110;
  wire       [0:0]    _zz_110;
  wire       [0:0]    ret_219;
  wire       [0:0]    x_tmp_219;
  wire       [0:0]    all_zeros_219;
  wire       [0:0]    all_left_zeros_219;
  wire       [1:0]    left_all_zeros_count_108;
  wire       [0:0]    _zz_111;
  wire       [0:0]    ret_220;
  wire       [0:0]    x_tmp_220;
  wire       [0:0]    all_zeros_220;
  wire       [0:0]    all_left_zeros_220;
  wire       [1:0]    left_non_all_zeros_count_104;
  wire       [1:0]    non_zeros_lzc_104;
  wire       [2:0]    left_all_zeros_count_109;
  wire       [1:0]    ret_221;
  wire       [1:0]    x_tmp_221;
  wire       [1:0]    all_zeros_221;
  wire       [1:0]    all_left_zeros_221;
  wire       [1:0]    whole_bit_count_111;
  wire       [1:0]    left_bit_count_111;
  wire       [0:0]    _zz_112;
  wire       [0:0]    ret_222;
  wire       [0:0]    x_tmp_222;
  wire       [0:0]    all_zeros_222;
  wire       [0:0]    all_left_zeros_222;
  wire       [1:0]    left_all_zeros_count_110;
  wire       [0:0]    _zz_113;
  wire       [0:0]    ret_223;
  wire       [0:0]    x_tmp_223;
  wire       [0:0]    all_zeros_223;
  wire       [0:0]    all_left_zeros_223;
  wire       [1:0]    left_non_all_zeros_count_105;
  wire       [1:0]    non_zeros_lzc_105;
  wire       [2:0]    left_non_all_zeros_count_106;
  wire       [2:0]    non_zeros_lzc_106;
  wire       [3:0]    left_non_all_zeros_count_107;
  wire       [3:0]    non_zeros_lzc_107;
  wire       [4:0]    left_non_all_zeros_count_108;
  wire       [4:0]    non_zeros_lzc_108;
  wire       [5:0]    left_all_zeros_count_111;
  wire       [4:0]    ret_224;
  wire       [15:0]   x_tmp_224;
  wire       [4:0]    all_zeros_224;
  wire       [4:0]    all_left_zeros_224;
  wire       [4:0]    whole_bit_count_112;
  wire       [4:0]    left_bit_count_112;
  wire       [3:0]    ret_225;
  wire       [7:0]    x_tmp_225;
  wire       [3:0]    all_zeros_225;
  wire       [3:0]    all_left_zeros_225;
  wire       [3:0]    whole_bit_count_113;
  wire       [3:0]    left_bit_count_113;
  wire       [2:0]    ret_226;
  wire       [3:0]    x_tmp_226;
  wire       [2:0]    all_zeros_226;
  wire       [2:0]    all_left_zeros_226;
  wire       [2:0]    whole_bit_count_114;
  wire       [2:0]    left_bit_count_114;
  wire       [1:0]    ret_227;
  wire       [1:0]    x_tmp_227;
  wire       [1:0]    all_zeros_227;
  wire       [1:0]    all_left_zeros_227;
  wire       [1:0]    whole_bit_count_115;
  wire       [1:0]    left_bit_count_115;
  wire       [0:0]    _zz_114;
  wire       [0:0]    ret_228;
  wire       [0:0]    x_tmp_228;
  wire       [0:0]    all_zeros_228;
  wire       [0:0]    all_left_zeros_228;
  wire       [1:0]    left_all_zeros_count_112;
  wire       [0:0]    _zz_115;
  wire       [0:0]    ret_229;
  wire       [0:0]    x_tmp_229;
  wire       [0:0]    all_zeros_229;
  wire       [0:0]    all_left_zeros_229;
  wire       [1:0]    left_non_all_zeros_count_109;
  wire       [1:0]    non_zeros_lzc_109;
  wire       [2:0]    left_all_zeros_count_113;
  wire       [1:0]    ret_230;
  wire       [1:0]    x_tmp_230;
  wire       [1:0]    all_zeros_230;
  wire       [1:0]    all_left_zeros_230;
  wire       [1:0]    whole_bit_count_116;
  wire       [1:0]    left_bit_count_116;
  wire       [0:0]    _zz_116;
  wire       [0:0]    ret_231;
  wire       [0:0]    x_tmp_231;
  wire       [0:0]    all_zeros_231;
  wire       [0:0]    all_left_zeros_231;
  wire       [1:0]    left_all_zeros_count_114;
  wire       [0:0]    _zz_117;
  wire       [0:0]    ret_232;
  wire       [0:0]    x_tmp_232;
  wire       [0:0]    all_zeros_232;
  wire       [0:0]    all_left_zeros_232;
  wire       [1:0]    left_non_all_zeros_count_110;
  wire       [1:0]    non_zeros_lzc_110;
  wire       [2:0]    left_non_all_zeros_count_111;
  wire       [2:0]    non_zeros_lzc_111;
  wire       [3:0]    left_all_zeros_count_115;
  wire       [2:0]    ret_233;
  wire       [3:0]    x_tmp_233;
  wire       [2:0]    all_zeros_233;
  wire       [2:0]    all_left_zeros_233;
  wire       [2:0]    whole_bit_count_117;
  wire       [2:0]    left_bit_count_117;
  wire       [1:0]    ret_234;
  wire       [1:0]    x_tmp_234;
  wire       [1:0]    all_zeros_234;
  wire       [1:0]    all_left_zeros_234;
  wire       [1:0]    whole_bit_count_118;
  wire       [1:0]    left_bit_count_118;
  wire       [0:0]    _zz_118;
  wire       [0:0]    ret_235;
  wire       [0:0]    x_tmp_235;
  wire       [0:0]    all_zeros_235;
  wire       [0:0]    all_left_zeros_235;
  wire       [1:0]    left_all_zeros_count_116;
  wire       [0:0]    _zz_119;
  wire       [0:0]    ret_236;
  wire       [0:0]    x_tmp_236;
  wire       [0:0]    all_zeros_236;
  wire       [0:0]    all_left_zeros_236;
  wire       [1:0]    left_non_all_zeros_count_112;
  wire       [1:0]    non_zeros_lzc_112;
  wire       [2:0]    left_all_zeros_count_117;
  wire       [1:0]    ret_237;
  wire       [1:0]    x_tmp_237;
  wire       [1:0]    all_zeros_237;
  wire       [1:0]    all_left_zeros_237;
  wire       [1:0]    whole_bit_count_119;
  wire       [1:0]    left_bit_count_119;
  wire       [0:0]    _zz_120;
  wire       [0:0]    ret_238;
  wire       [0:0]    x_tmp_238;
  wire       [0:0]    all_zeros_238;
  wire       [0:0]    all_left_zeros_238;
  wire       [1:0]    left_all_zeros_count_118;
  wire       [0:0]    _zz_121;
  wire       [0:0]    ret_239;
  wire       [0:0]    x_tmp_239;
  wire       [0:0]    all_zeros_239;
  wire       [0:0]    all_left_zeros_239;
  wire       [1:0]    left_non_all_zeros_count_113;
  wire       [1:0]    non_zeros_lzc_113;
  wire       [2:0]    left_non_all_zeros_count_114;
  wire       [2:0]    non_zeros_lzc_114;
  wire       [3:0]    left_non_all_zeros_count_115;
  wire       [3:0]    non_zeros_lzc_115;
  wire       [4:0]    left_all_zeros_count_119;
  wire       [3:0]    ret_240;
  wire       [7:0]    x_tmp_240;
  wire       [3:0]    all_zeros_240;
  wire       [3:0]    all_left_zeros_240;
  wire       [3:0]    whole_bit_count_120;
  wire       [3:0]    left_bit_count_120;
  wire       [2:0]    ret_241;
  wire       [3:0]    x_tmp_241;
  wire       [2:0]    all_zeros_241;
  wire       [2:0]    all_left_zeros_241;
  wire       [2:0]    whole_bit_count_121;
  wire       [2:0]    left_bit_count_121;
  wire       [1:0]    ret_242;
  wire       [1:0]    x_tmp_242;
  wire       [1:0]    all_zeros_242;
  wire       [1:0]    all_left_zeros_242;
  wire       [1:0]    whole_bit_count_122;
  wire       [1:0]    left_bit_count_122;
  wire       [0:0]    _zz_122;
  wire       [0:0]    ret_243;
  wire       [0:0]    x_tmp_243;
  wire       [0:0]    all_zeros_243;
  wire       [0:0]    all_left_zeros_243;
  wire       [1:0]    left_all_zeros_count_120;
  wire       [0:0]    _zz_123;
  wire       [0:0]    ret_244;
  wire       [0:0]    x_tmp_244;
  wire       [0:0]    all_zeros_244;
  wire       [0:0]    all_left_zeros_244;
  wire       [1:0]    left_non_all_zeros_count_116;
  wire       [1:0]    non_zeros_lzc_116;
  wire       [2:0]    left_all_zeros_count_121;
  wire       [1:0]    ret_245;
  wire       [1:0]    x_tmp_245;
  wire       [1:0]    all_zeros_245;
  wire       [1:0]    all_left_zeros_245;
  wire       [1:0]    whole_bit_count_123;
  wire       [1:0]    left_bit_count_123;
  wire       [0:0]    _zz_124;
  wire       [0:0]    ret_246;
  wire       [0:0]    x_tmp_246;
  wire       [0:0]    all_zeros_246;
  wire       [0:0]    all_left_zeros_246;
  wire       [1:0]    left_all_zeros_count_122;
  wire       [0:0]    _zz_125;
  wire       [0:0]    ret_247;
  wire       [0:0]    x_tmp_247;
  wire       [0:0]    all_zeros_247;
  wire       [0:0]    all_left_zeros_247;
  wire       [1:0]    left_non_all_zeros_count_117;
  wire       [1:0]    non_zeros_lzc_117;
  wire       [2:0]    left_non_all_zeros_count_118;
  wire       [2:0]    non_zeros_lzc_118;
  wire       [3:0]    left_all_zeros_count_123;
  wire       [2:0]    ret_248;
  wire       [3:0]    x_tmp_248;
  wire       [2:0]    all_zeros_248;
  wire       [2:0]    all_left_zeros_248;
  wire       [2:0]    whole_bit_count_124;
  wire       [2:0]    left_bit_count_124;
  wire       [1:0]    ret_249;
  wire       [1:0]    x_tmp_249;
  wire       [1:0]    all_zeros_249;
  wire       [1:0]    all_left_zeros_249;
  wire       [1:0]    whole_bit_count_125;
  wire       [1:0]    left_bit_count_125;
  wire       [0:0]    _zz_126;
  wire       [0:0]    ret_250;
  wire       [0:0]    x_tmp_250;
  wire       [0:0]    all_zeros_250;
  wire       [0:0]    all_left_zeros_250;
  wire       [1:0]    left_all_zeros_count_124;
  wire       [0:0]    _zz_127;
  wire       [0:0]    ret_251;
  wire       [0:0]    x_tmp_251;
  wire       [0:0]    all_zeros_251;
  wire       [0:0]    all_left_zeros_251;
  wire       [1:0]    left_non_all_zeros_count_119;
  wire       [1:0]    non_zeros_lzc_119;
  wire       [2:0]    left_all_zeros_count_125;
  wire       [1:0]    ret_252;
  wire       [1:0]    x_tmp_252;
  wire       [1:0]    all_zeros_252;
  wire       [1:0]    all_left_zeros_252;
  wire       [1:0]    whole_bit_count_126;
  wire       [1:0]    left_bit_count_126;
  wire       [0:0]    _zz_128;
  wire       [0:0]    ret_253;
  wire       [0:0]    x_tmp_253;
  wire       [0:0]    all_zeros_253;
  wire       [0:0]    all_left_zeros_253;
  wire       [1:0]    left_all_zeros_count_126;
  wire       [0:0]    _zz_129;
  wire       [0:0]    ret_254;
  wire       [0:0]    x_tmp_254;
  wire       [0:0]    all_zeros_254;
  wire       [0:0]    all_left_zeros_254;
  wire       [1:0]    left_non_all_zeros_count_120;
  wire       [1:0]    non_zeros_lzc_120;
  wire       [2:0]    left_non_all_zeros_count_121;
  wire       [2:0]    non_zeros_lzc_121;
  wire       [3:0]    left_non_all_zeros_count_122;
  wire       [3:0]    non_zeros_lzc_122;
  wire       [4:0]    left_non_all_zeros_count_123;
  wire       [4:0]    non_zeros_lzc_123;
  wire       [5:0]    left_non_all_zeros_count_124;
  wire       [5:0]    non_zeros_lzc_124;
  wire       [6:0]    left_non_all_zeros_count_125;
  wire       [6:0]    non_zeros_lzc_125;
  wire       [7:0]    left_non_all_zeros_count_126;
  wire       [7:0]    non_zeros_lzc_126;
  reg        [7:0]    lzc_t;
  wire       [6:0]    lzc;
  reg        [31:0]   exp_func_value_real;
  reg        [31:0]   exp_func_value_imag;
  wire       [6:0]    position;
  wire       [31:0]   x1;
  wire       [31:0]   x2;
  wire       [31:0]   y1_real;
  wire       [31:0]   y1_imag;
  wire       [31:0]   y2_real;
  wire       [31:0]   y2_imag;
  wire       [31:0]   xd;
  wire       [31:0]   yd_real;
  wire       [31:0]   yd_imag;
  reg        [31:0]   k_real;
  reg        [31:0]   k_imag;
  wire       [31:0]   _zz_130;
  wire       [31:0]   deltax_real;
  wire       [31:0]   deltax_imag;
  wire       [63:0]   _zz_131;
  wire       [63:0]   pre_exp_indx_real;
  wire       [63:0]   pre_exp_indx_imag;
  reg        [63:0]   pre_exp_indx_regNext_real;
  reg        [63:0]   pre_exp_indx_regNext_imag;
  reg        [31:0]   pfunc_tb_127_regNext_real;
  reg        [31:0]   pfunc_tb_127_regNext_imag;
  reg        [31:0]   pfunc_tb_0_regNext_real;
  reg        [31:0]   pfunc_tb_0_regNext_imag;
  reg        [31:0]   exp_func_value_regNext_real;
  reg        [31:0]   exp_func_value_regNext_imag;

  assign _zz_151 = (xd == 32'h0);
  assign _zz_152 = (lzc != 7'h0);
  assign _zz_153 = (lzc_t[7] == 1'b1);
  assign _zz_154 = _zz_155;
  assign _zz_155 = ($signed(fixTo_dout) % $signed(_zz_156));
  assign _zz_156 = fx_period;
  assign _zz_157 = (left_bit_count_6 + _zz_159);
  assign _zz_158 = (! _zz_2[0]);
  assign _zz_159 = {1'd0, _zz_158};
  assign _zz_160 = (! _zz_3[0]);
  assign _zz_161 = {1'd0, _zz_160};
  assign _zz_162 = (left_bit_count_5 + _zz_163);
  assign _zz_163 = {1'd0, ret_6};
  assign _zz_164 = (left_bit_count_7 + _zz_166);
  assign _zz_165 = (! _zz_4[0]);
  assign _zz_166 = {1'd0, _zz_165};
  assign _zz_167 = (! _zz_5[0]);
  assign _zz_168 = {1'd0, _zz_167};
  assign _zz_169 = {1'd0, ret_9};
  assign _zz_170 = (left_bit_count_4 + _zz_171);
  assign _zz_171 = {1'd0, ret_5};
  assign _zz_172 = (left_bit_count_9 + _zz_174);
  assign _zz_173 = (! _zz_6[0]);
  assign _zz_174 = {1'd0, _zz_173};
  assign _zz_175 = (! _zz_7[0]);
  assign _zz_176 = {1'd0, _zz_175};
  assign _zz_177 = (left_bit_count_8 + _zz_178);
  assign _zz_178 = {1'd0, ret_13};
  assign _zz_179 = (left_bit_count_10 + _zz_181);
  assign _zz_180 = (! _zz_8[0]);
  assign _zz_181 = {1'd0, _zz_180};
  assign _zz_182 = (! _zz_9[0]);
  assign _zz_183 = {1'd0, _zz_182};
  assign _zz_184 = {1'd0, ret_16};
  assign _zz_185 = {1'd0, ret_12};
  assign _zz_186 = (left_bit_count_3 + _zz_187);
  assign _zz_187 = {1'd0, ret_4};
  assign _zz_188 = (left_bit_count_13 + _zz_190);
  assign _zz_189 = (! _zz_10[0]);
  assign _zz_190 = {1'd0, _zz_189};
  assign _zz_191 = (! _zz_11[0]);
  assign _zz_192 = {1'd0, _zz_191};
  assign _zz_193 = (left_bit_count_12 + _zz_194);
  assign _zz_194 = {1'd0, ret_21};
  assign _zz_195 = (left_bit_count_14 + _zz_197);
  assign _zz_196 = (! _zz_12[0]);
  assign _zz_197 = {1'd0, _zz_196};
  assign _zz_198 = (! _zz_13[0]);
  assign _zz_199 = {1'd0, _zz_198};
  assign _zz_200 = {1'd0, ret_24};
  assign _zz_201 = (left_bit_count_11 + _zz_202);
  assign _zz_202 = {1'd0, ret_20};
  assign _zz_203 = (left_bit_count_16 + _zz_205);
  assign _zz_204 = (! _zz_14[0]);
  assign _zz_205 = {1'd0, _zz_204};
  assign _zz_206 = (! _zz_15[0]);
  assign _zz_207 = {1'd0, _zz_206};
  assign _zz_208 = (left_bit_count_15 + _zz_209);
  assign _zz_209 = {1'd0, ret_28};
  assign _zz_210 = (left_bit_count_17 + _zz_212);
  assign _zz_211 = (! _zz_16[0]);
  assign _zz_212 = {1'd0, _zz_211};
  assign _zz_213 = (! _zz_17[0]);
  assign _zz_214 = {1'd0, _zz_213};
  assign _zz_215 = {1'd0, ret_31};
  assign _zz_216 = {1'd0, ret_27};
  assign _zz_217 = {1'd0, ret_19};
  assign _zz_218 = (left_bit_count_2 + _zz_219);
  assign _zz_219 = {1'd0, ret_3};
  assign _zz_220 = (left_bit_count_21 + _zz_222);
  assign _zz_221 = (! _zz_18[0]);
  assign _zz_222 = {1'd0, _zz_221};
  assign _zz_223 = (! _zz_19[0]);
  assign _zz_224 = {1'd0, _zz_223};
  assign _zz_225 = (left_bit_count_20 + _zz_226);
  assign _zz_226 = {1'd0, ret_37};
  assign _zz_227 = (left_bit_count_22 + _zz_229);
  assign _zz_228 = (! _zz_20[0]);
  assign _zz_229 = {1'd0, _zz_228};
  assign _zz_230 = (! _zz_21[0]);
  assign _zz_231 = {1'd0, _zz_230};
  assign _zz_232 = {1'd0, ret_40};
  assign _zz_233 = (left_bit_count_19 + _zz_234);
  assign _zz_234 = {1'd0, ret_36};
  assign _zz_235 = (left_bit_count_24 + _zz_237);
  assign _zz_236 = (! _zz_22[0]);
  assign _zz_237 = {1'd0, _zz_236};
  assign _zz_238 = (! _zz_23[0]);
  assign _zz_239 = {1'd0, _zz_238};
  assign _zz_240 = (left_bit_count_23 + _zz_241);
  assign _zz_241 = {1'd0, ret_44};
  assign _zz_242 = (left_bit_count_25 + _zz_244);
  assign _zz_243 = (! _zz_24[0]);
  assign _zz_244 = {1'd0, _zz_243};
  assign _zz_245 = (! _zz_25[0]);
  assign _zz_246 = {1'd0, _zz_245};
  assign _zz_247 = {1'd0, ret_47};
  assign _zz_248 = {1'd0, ret_43};
  assign _zz_249 = (left_bit_count_18 + _zz_250);
  assign _zz_250 = {1'd0, ret_35};
  assign _zz_251 = (left_bit_count_28 + _zz_253);
  assign _zz_252 = (! _zz_26[0]);
  assign _zz_253 = {1'd0, _zz_252};
  assign _zz_254 = (! _zz_27[0]);
  assign _zz_255 = {1'd0, _zz_254};
  assign _zz_256 = (left_bit_count_27 + _zz_257);
  assign _zz_257 = {1'd0, ret_52};
  assign _zz_258 = (left_bit_count_29 + _zz_260);
  assign _zz_259 = (! _zz_28[0]);
  assign _zz_260 = {1'd0, _zz_259};
  assign _zz_261 = (! _zz_29[0]);
  assign _zz_262 = {1'd0, _zz_261};
  assign _zz_263 = {1'd0, ret_55};
  assign _zz_264 = (left_bit_count_26 + _zz_265);
  assign _zz_265 = {1'd0, ret_51};
  assign _zz_266 = (left_bit_count_31 + _zz_268);
  assign _zz_267 = (! _zz_30[0]);
  assign _zz_268 = {1'd0, _zz_267};
  assign _zz_269 = (! _zz_31[0]);
  assign _zz_270 = {1'd0, _zz_269};
  assign _zz_271 = (left_bit_count_30 + _zz_272);
  assign _zz_272 = {1'd0, ret_59};
  assign _zz_273 = (left_bit_count_32 + _zz_275);
  assign _zz_274 = (! _zz_32[0]);
  assign _zz_275 = {1'd0, _zz_274};
  assign _zz_276 = (! _zz_33[0]);
  assign _zz_277 = {1'd0, _zz_276};
  assign _zz_278 = {1'd0, ret_62};
  assign _zz_279 = {1'd0, ret_58};
  assign _zz_280 = {1'd0, ret_50};
  assign _zz_281 = {1'd0, ret_34};
  assign _zz_282 = (left_bit_count_1 + _zz_283);
  assign _zz_283 = {1'd0, ret_2};
  assign _zz_284 = (left_bit_count_37 + _zz_286);
  assign _zz_285 = (! _zz_34[0]);
  assign _zz_286 = {1'd0, _zz_285};
  assign _zz_287 = (! _zz_35[0]);
  assign _zz_288 = {1'd0, _zz_287};
  assign _zz_289 = (left_bit_count_36 + _zz_290);
  assign _zz_290 = {1'd0, ret_69};
  assign _zz_291 = (left_bit_count_38 + _zz_293);
  assign _zz_292 = (! _zz_36[0]);
  assign _zz_293 = {1'd0, _zz_292};
  assign _zz_294 = (! _zz_37[0]);
  assign _zz_295 = {1'd0, _zz_294};
  assign _zz_296 = {1'd0, ret_72};
  assign _zz_297 = (left_bit_count_35 + _zz_298);
  assign _zz_298 = {1'd0, ret_68};
  assign _zz_299 = (left_bit_count_40 + _zz_301);
  assign _zz_300 = (! _zz_38[0]);
  assign _zz_301 = {1'd0, _zz_300};
  assign _zz_302 = (! _zz_39[0]);
  assign _zz_303 = {1'd0, _zz_302};
  assign _zz_304 = (left_bit_count_39 + _zz_305);
  assign _zz_305 = {1'd0, ret_76};
  assign _zz_306 = (left_bit_count_41 + _zz_308);
  assign _zz_307 = (! _zz_40[0]);
  assign _zz_308 = {1'd0, _zz_307};
  assign _zz_309 = (! _zz_41[0]);
  assign _zz_310 = {1'd0, _zz_309};
  assign _zz_311 = {1'd0, ret_79};
  assign _zz_312 = {1'd0, ret_75};
  assign _zz_313 = (left_bit_count_34 + _zz_314);
  assign _zz_314 = {1'd0, ret_67};
  assign _zz_315 = (left_bit_count_44 + _zz_317);
  assign _zz_316 = (! _zz_42[0]);
  assign _zz_317 = {1'd0, _zz_316};
  assign _zz_318 = (! _zz_43[0]);
  assign _zz_319 = {1'd0, _zz_318};
  assign _zz_320 = (left_bit_count_43 + _zz_321);
  assign _zz_321 = {1'd0, ret_84};
  assign _zz_322 = (left_bit_count_45 + _zz_324);
  assign _zz_323 = (! _zz_44[0]);
  assign _zz_324 = {1'd0, _zz_323};
  assign _zz_325 = (! _zz_45[0]);
  assign _zz_326 = {1'd0, _zz_325};
  assign _zz_327 = {1'd0, ret_87};
  assign _zz_328 = (left_bit_count_42 + _zz_329);
  assign _zz_329 = {1'd0, ret_83};
  assign _zz_330 = (left_bit_count_47 + _zz_332);
  assign _zz_331 = (! _zz_46[0]);
  assign _zz_332 = {1'd0, _zz_331};
  assign _zz_333 = (! _zz_47[0]);
  assign _zz_334 = {1'd0, _zz_333};
  assign _zz_335 = (left_bit_count_46 + _zz_336);
  assign _zz_336 = {1'd0, ret_91};
  assign _zz_337 = (left_bit_count_48 + _zz_339);
  assign _zz_338 = (! _zz_48[0]);
  assign _zz_339 = {1'd0, _zz_338};
  assign _zz_340 = (! _zz_49[0]);
  assign _zz_341 = {1'd0, _zz_340};
  assign _zz_342 = {1'd0, ret_94};
  assign _zz_343 = {1'd0, ret_90};
  assign _zz_344 = {1'd0, ret_82};
  assign _zz_345 = (left_bit_count_33 + _zz_346);
  assign _zz_346 = {1'd0, ret_66};
  assign _zz_347 = (left_bit_count_52 + _zz_349);
  assign _zz_348 = (! _zz_50[0]);
  assign _zz_349 = {1'd0, _zz_348};
  assign _zz_350 = (! _zz_51[0]);
  assign _zz_351 = {1'd0, _zz_350};
  assign _zz_352 = (left_bit_count_51 + _zz_353);
  assign _zz_353 = {1'd0, ret_100};
  assign _zz_354 = (left_bit_count_53 + _zz_356);
  assign _zz_355 = (! _zz_52[0]);
  assign _zz_356 = {1'd0, _zz_355};
  assign _zz_357 = (! _zz_53[0]);
  assign _zz_358 = {1'd0, _zz_357};
  assign _zz_359 = {1'd0, ret_103};
  assign _zz_360 = (left_bit_count_50 + _zz_361);
  assign _zz_361 = {1'd0, ret_99};
  assign _zz_362 = (left_bit_count_55 + _zz_364);
  assign _zz_363 = (! _zz_54[0]);
  assign _zz_364 = {1'd0, _zz_363};
  assign _zz_365 = (! _zz_55[0]);
  assign _zz_366 = {1'd0, _zz_365};
  assign _zz_367 = (left_bit_count_54 + _zz_368);
  assign _zz_368 = {1'd0, ret_107};
  assign _zz_369 = (left_bit_count_56 + _zz_371);
  assign _zz_370 = (! _zz_56[0]);
  assign _zz_371 = {1'd0, _zz_370};
  assign _zz_372 = (! _zz_57[0]);
  assign _zz_373 = {1'd0, _zz_372};
  assign _zz_374 = {1'd0, ret_110};
  assign _zz_375 = {1'd0, ret_106};
  assign _zz_376 = (left_bit_count_49 + _zz_377);
  assign _zz_377 = {1'd0, ret_98};
  assign _zz_378 = (left_bit_count_59 + _zz_380);
  assign _zz_379 = (! _zz_58[0]);
  assign _zz_380 = {1'd0, _zz_379};
  assign _zz_381 = (! _zz_59[0]);
  assign _zz_382 = {1'd0, _zz_381};
  assign _zz_383 = (left_bit_count_58 + _zz_384);
  assign _zz_384 = {1'd0, ret_115};
  assign _zz_385 = (left_bit_count_60 + _zz_387);
  assign _zz_386 = (! _zz_60[0]);
  assign _zz_387 = {1'd0, _zz_386};
  assign _zz_388 = (! _zz_61[0]);
  assign _zz_389 = {1'd0, _zz_388};
  assign _zz_390 = {1'd0, ret_118};
  assign _zz_391 = (left_bit_count_57 + _zz_392);
  assign _zz_392 = {1'd0, ret_114};
  assign _zz_393 = (left_bit_count_62 + _zz_395);
  assign _zz_394 = (! _zz_62[0]);
  assign _zz_395 = {1'd0, _zz_394};
  assign _zz_396 = (! _zz_63[0]);
  assign _zz_397 = {1'd0, _zz_396};
  assign _zz_398 = (left_bit_count_61 + _zz_399);
  assign _zz_399 = {1'd0, ret_122};
  assign _zz_400 = (left_bit_count_63 + _zz_402);
  assign _zz_401 = (! _zz_64[0]);
  assign _zz_402 = {1'd0, _zz_401};
  assign _zz_403 = (! _zz_65[0]);
  assign _zz_404 = {1'd0, _zz_403};
  assign _zz_405 = {1'd0, ret_125};
  assign _zz_406 = {1'd0, ret_121};
  assign _zz_407 = {1'd0, ret_113};
  assign _zz_408 = {1'd0, ret_97};
  assign _zz_409 = {1'd0, ret_65};
  assign _zz_410 = (left_bit_count + _zz_411);
  assign _zz_411 = {1'd0, ret_1};
  assign _zz_412 = (left_bit_count_69 + _zz_414);
  assign _zz_413 = (! _zz_66[0]);
  assign _zz_414 = {1'd0, _zz_413};
  assign _zz_415 = (! _zz_67[0]);
  assign _zz_416 = {1'd0, _zz_415};
  assign _zz_417 = (left_bit_count_68 + _zz_418);
  assign _zz_418 = {1'd0, ret_133};
  assign _zz_419 = (left_bit_count_70 + _zz_421);
  assign _zz_420 = (! _zz_68[0]);
  assign _zz_421 = {1'd0, _zz_420};
  assign _zz_422 = (! _zz_69[0]);
  assign _zz_423 = {1'd0, _zz_422};
  assign _zz_424 = {1'd0, ret_136};
  assign _zz_425 = (left_bit_count_67 + _zz_426);
  assign _zz_426 = {1'd0, ret_132};
  assign _zz_427 = (left_bit_count_72 + _zz_429);
  assign _zz_428 = (! _zz_70[0]);
  assign _zz_429 = {1'd0, _zz_428};
  assign _zz_430 = (! _zz_71[0]);
  assign _zz_431 = {1'd0, _zz_430};
  assign _zz_432 = (left_bit_count_71 + _zz_433);
  assign _zz_433 = {1'd0, ret_140};
  assign _zz_434 = (left_bit_count_73 + _zz_436);
  assign _zz_435 = (! _zz_72[0]);
  assign _zz_436 = {1'd0, _zz_435};
  assign _zz_437 = (! _zz_73[0]);
  assign _zz_438 = {1'd0, _zz_437};
  assign _zz_439 = {1'd0, ret_143};
  assign _zz_440 = {1'd0, ret_139};
  assign _zz_441 = (left_bit_count_66 + _zz_442);
  assign _zz_442 = {1'd0, ret_131};
  assign _zz_443 = (left_bit_count_76 + _zz_445);
  assign _zz_444 = (! _zz_74[0]);
  assign _zz_445 = {1'd0, _zz_444};
  assign _zz_446 = (! _zz_75[0]);
  assign _zz_447 = {1'd0, _zz_446};
  assign _zz_448 = (left_bit_count_75 + _zz_449);
  assign _zz_449 = {1'd0, ret_148};
  assign _zz_450 = (left_bit_count_77 + _zz_452);
  assign _zz_451 = (! _zz_76[0]);
  assign _zz_452 = {1'd0, _zz_451};
  assign _zz_453 = (! _zz_77[0]);
  assign _zz_454 = {1'd0, _zz_453};
  assign _zz_455 = {1'd0, ret_151};
  assign _zz_456 = (left_bit_count_74 + _zz_457);
  assign _zz_457 = {1'd0, ret_147};
  assign _zz_458 = (left_bit_count_79 + _zz_460);
  assign _zz_459 = (! _zz_78[0]);
  assign _zz_460 = {1'd0, _zz_459};
  assign _zz_461 = (! _zz_79[0]);
  assign _zz_462 = {1'd0, _zz_461};
  assign _zz_463 = (left_bit_count_78 + _zz_464);
  assign _zz_464 = {1'd0, ret_155};
  assign _zz_465 = (left_bit_count_80 + _zz_467);
  assign _zz_466 = (! _zz_80[0]);
  assign _zz_467 = {1'd0, _zz_466};
  assign _zz_468 = (! _zz_81[0]);
  assign _zz_469 = {1'd0, _zz_468};
  assign _zz_470 = {1'd0, ret_158};
  assign _zz_471 = {1'd0, ret_154};
  assign _zz_472 = {1'd0, ret_146};
  assign _zz_473 = (left_bit_count_65 + _zz_474);
  assign _zz_474 = {1'd0, ret_130};
  assign _zz_475 = (left_bit_count_84 + _zz_477);
  assign _zz_476 = (! _zz_82[0]);
  assign _zz_477 = {1'd0, _zz_476};
  assign _zz_478 = (! _zz_83[0]);
  assign _zz_479 = {1'd0, _zz_478};
  assign _zz_480 = (left_bit_count_83 + _zz_481);
  assign _zz_481 = {1'd0, ret_164};
  assign _zz_482 = (left_bit_count_85 + _zz_484);
  assign _zz_483 = (! _zz_84[0]);
  assign _zz_484 = {1'd0, _zz_483};
  assign _zz_485 = (! _zz_85[0]);
  assign _zz_486 = {1'd0, _zz_485};
  assign _zz_487 = {1'd0, ret_167};
  assign _zz_488 = (left_bit_count_82 + _zz_489);
  assign _zz_489 = {1'd0, ret_163};
  assign _zz_490 = (left_bit_count_87 + _zz_492);
  assign _zz_491 = (! _zz_86[0]);
  assign _zz_492 = {1'd0, _zz_491};
  assign _zz_493 = (! _zz_87[0]);
  assign _zz_494 = {1'd0, _zz_493};
  assign _zz_495 = (left_bit_count_86 + _zz_496);
  assign _zz_496 = {1'd0, ret_171};
  assign _zz_497 = (left_bit_count_88 + _zz_499);
  assign _zz_498 = (! _zz_88[0]);
  assign _zz_499 = {1'd0, _zz_498};
  assign _zz_500 = (! _zz_89[0]);
  assign _zz_501 = {1'd0, _zz_500};
  assign _zz_502 = {1'd0, ret_174};
  assign _zz_503 = {1'd0, ret_170};
  assign _zz_504 = (left_bit_count_81 + _zz_505);
  assign _zz_505 = {1'd0, ret_162};
  assign _zz_506 = (left_bit_count_91 + _zz_508);
  assign _zz_507 = (! _zz_90[0]);
  assign _zz_508 = {1'd0, _zz_507};
  assign _zz_509 = (! _zz_91[0]);
  assign _zz_510 = {1'd0, _zz_509};
  assign _zz_511 = (left_bit_count_90 + _zz_512);
  assign _zz_512 = {1'd0, ret_179};
  assign _zz_513 = (left_bit_count_92 + _zz_515);
  assign _zz_514 = (! _zz_92[0]);
  assign _zz_515 = {1'd0, _zz_514};
  assign _zz_516 = (! _zz_93[0]);
  assign _zz_517 = {1'd0, _zz_516};
  assign _zz_518 = {1'd0, ret_182};
  assign _zz_519 = (left_bit_count_89 + _zz_520);
  assign _zz_520 = {1'd0, ret_178};
  assign _zz_521 = (left_bit_count_94 + _zz_523);
  assign _zz_522 = (! _zz_94[0]);
  assign _zz_523 = {1'd0, _zz_522};
  assign _zz_524 = (! _zz_95[0]);
  assign _zz_525 = {1'd0, _zz_524};
  assign _zz_526 = (left_bit_count_93 + _zz_527);
  assign _zz_527 = {1'd0, ret_186};
  assign _zz_528 = (left_bit_count_95 + _zz_530);
  assign _zz_529 = (! _zz_96[0]);
  assign _zz_530 = {1'd0, _zz_529};
  assign _zz_531 = (! _zz_97[0]);
  assign _zz_532 = {1'd0, _zz_531};
  assign _zz_533 = {1'd0, ret_189};
  assign _zz_534 = {1'd0, ret_185};
  assign _zz_535 = {1'd0, ret_177};
  assign _zz_536 = {1'd0, ret_161};
  assign _zz_537 = (left_bit_count_64 + _zz_538);
  assign _zz_538 = {1'd0, ret_129};
  assign _zz_539 = (left_bit_count_100 + _zz_541);
  assign _zz_540 = (! _zz_98[0]);
  assign _zz_541 = {1'd0, _zz_540};
  assign _zz_542 = (! _zz_99[0]);
  assign _zz_543 = {1'd0, _zz_542};
  assign _zz_544 = (left_bit_count_99 + _zz_545);
  assign _zz_545 = {1'd0, ret_196};
  assign _zz_546 = (left_bit_count_101 + _zz_548);
  assign _zz_547 = (! _zz_100[0]);
  assign _zz_548 = {1'd0, _zz_547};
  assign _zz_549 = (! _zz_101[0]);
  assign _zz_550 = {1'd0, _zz_549};
  assign _zz_551 = {1'd0, ret_199};
  assign _zz_552 = (left_bit_count_98 + _zz_553);
  assign _zz_553 = {1'd0, ret_195};
  assign _zz_554 = (left_bit_count_103 + _zz_556);
  assign _zz_555 = (! _zz_102[0]);
  assign _zz_556 = {1'd0, _zz_555};
  assign _zz_557 = (! _zz_103[0]);
  assign _zz_558 = {1'd0, _zz_557};
  assign _zz_559 = (left_bit_count_102 + _zz_560);
  assign _zz_560 = {1'd0, ret_203};
  assign _zz_561 = (left_bit_count_104 + _zz_563);
  assign _zz_562 = (! _zz_104[0]);
  assign _zz_563 = {1'd0, _zz_562};
  assign _zz_564 = (! _zz_105[0]);
  assign _zz_565 = {1'd0, _zz_564};
  assign _zz_566 = {1'd0, ret_206};
  assign _zz_567 = {1'd0, ret_202};
  assign _zz_568 = (left_bit_count_97 + _zz_569);
  assign _zz_569 = {1'd0, ret_194};
  assign _zz_570 = (left_bit_count_107 + _zz_572);
  assign _zz_571 = (! _zz_106[0]);
  assign _zz_572 = {1'd0, _zz_571};
  assign _zz_573 = (! _zz_107[0]);
  assign _zz_574 = {1'd0, _zz_573};
  assign _zz_575 = (left_bit_count_106 + _zz_576);
  assign _zz_576 = {1'd0, ret_211};
  assign _zz_577 = (left_bit_count_108 + _zz_579);
  assign _zz_578 = (! _zz_108[0]);
  assign _zz_579 = {1'd0, _zz_578};
  assign _zz_580 = (! _zz_109[0]);
  assign _zz_581 = {1'd0, _zz_580};
  assign _zz_582 = {1'd0, ret_214};
  assign _zz_583 = (left_bit_count_105 + _zz_584);
  assign _zz_584 = {1'd0, ret_210};
  assign _zz_585 = (left_bit_count_110 + _zz_587);
  assign _zz_586 = (! _zz_110[0]);
  assign _zz_587 = {1'd0, _zz_586};
  assign _zz_588 = (! _zz_111[0]);
  assign _zz_589 = {1'd0, _zz_588};
  assign _zz_590 = (left_bit_count_109 + _zz_591);
  assign _zz_591 = {1'd0, ret_218};
  assign _zz_592 = (left_bit_count_111 + _zz_594);
  assign _zz_593 = (! _zz_112[0]);
  assign _zz_594 = {1'd0, _zz_593};
  assign _zz_595 = (! _zz_113[0]);
  assign _zz_596 = {1'd0, _zz_595};
  assign _zz_597 = {1'd0, ret_221};
  assign _zz_598 = {1'd0, ret_217};
  assign _zz_599 = {1'd0, ret_209};
  assign _zz_600 = (left_bit_count_96 + _zz_601);
  assign _zz_601 = {1'd0, ret_193};
  assign _zz_602 = (left_bit_count_115 + _zz_604);
  assign _zz_603 = (! _zz_114[0]);
  assign _zz_604 = {1'd0, _zz_603};
  assign _zz_605 = (! _zz_115[0]);
  assign _zz_606 = {1'd0, _zz_605};
  assign _zz_607 = (left_bit_count_114 + _zz_608);
  assign _zz_608 = {1'd0, ret_227};
  assign _zz_609 = (left_bit_count_116 + _zz_611);
  assign _zz_610 = (! _zz_116[0]);
  assign _zz_611 = {1'd0, _zz_610};
  assign _zz_612 = (! _zz_117[0]);
  assign _zz_613 = {1'd0, _zz_612};
  assign _zz_614 = {1'd0, ret_230};
  assign _zz_615 = (left_bit_count_113 + _zz_616);
  assign _zz_616 = {1'd0, ret_226};
  assign _zz_617 = (left_bit_count_118 + _zz_619);
  assign _zz_618 = (! _zz_118[0]);
  assign _zz_619 = {1'd0, _zz_618};
  assign _zz_620 = (! _zz_119[0]);
  assign _zz_621 = {1'd0, _zz_620};
  assign _zz_622 = (left_bit_count_117 + _zz_623);
  assign _zz_623 = {1'd0, ret_234};
  assign _zz_624 = (left_bit_count_119 + _zz_626);
  assign _zz_625 = (! _zz_120[0]);
  assign _zz_626 = {1'd0, _zz_625};
  assign _zz_627 = (! _zz_121[0]);
  assign _zz_628 = {1'd0, _zz_627};
  assign _zz_629 = {1'd0, ret_237};
  assign _zz_630 = {1'd0, ret_233};
  assign _zz_631 = (left_bit_count_112 + _zz_632);
  assign _zz_632 = {1'd0, ret_225};
  assign _zz_633 = (left_bit_count_122 + _zz_635);
  assign _zz_634 = (! _zz_122[0]);
  assign _zz_635 = {1'd0, _zz_634};
  assign _zz_636 = (! _zz_123[0]);
  assign _zz_637 = {1'd0, _zz_636};
  assign _zz_638 = (left_bit_count_121 + _zz_639);
  assign _zz_639 = {1'd0, ret_242};
  assign _zz_640 = (left_bit_count_123 + _zz_642);
  assign _zz_641 = (! _zz_124[0]);
  assign _zz_642 = {1'd0, _zz_641};
  assign _zz_643 = (! _zz_125[0]);
  assign _zz_644 = {1'd0, _zz_643};
  assign _zz_645 = {1'd0, ret_245};
  assign _zz_646 = (left_bit_count_120 + _zz_647);
  assign _zz_647 = {1'd0, ret_241};
  assign _zz_648 = (left_bit_count_125 + _zz_650);
  assign _zz_649 = (! _zz_126[0]);
  assign _zz_650 = {1'd0, _zz_649};
  assign _zz_651 = (! _zz_127[0]);
  assign _zz_652 = {1'd0, _zz_651};
  assign _zz_653 = (left_bit_count_124 + _zz_654);
  assign _zz_654 = {1'd0, ret_249};
  assign _zz_655 = (left_bit_count_126 + _zz_657);
  assign _zz_656 = (! _zz_128[0]);
  assign _zz_657 = {1'd0, _zz_656};
  assign _zz_658 = (! _zz_129[0]);
  assign _zz_659 = {1'd0, _zz_658};
  assign _zz_660 = {1'd0, ret_252};
  assign _zz_661 = {1'd0, ret_248};
  assign _zz_662 = {1'd0, ret_240};
  assign _zz_663 = {1'd0, ret_224};
  assign _zz_664 = {1'd0, ret_192};
  assign _zz_665 = {1'd0, ret_128};
  assign _zz_666 = _zz_145;
  assign _zz_667 = _zz_148;
  assign _zz_668 = fixTo_3_dout;
  assign _zz_669 = 32'hffffffff;
  assign _zz_670 = 32'hffffffff;
  assign _zz_671 = fixTo_1_dout;
  assign _zz_672 = {fixTo_4_dout,_zz_673};
  assign _zz_673 = 32'h0;
  assign _zz_674 = _zz_130;
  assign _zz_675 = fixTo_6_dout;
  assign _zz_676 = fixTo_2_dout;
  assign _zz_677 = {fixTo_7_dout,_zz_678};
  assign _zz_678 = 32'h0;
  assign _zz_679 = _zz_130;
  assign _zz_680 = fixTo_9_dout;
  assign _zz_681 = ($signed(k_real) + $signed(k_imag));
  assign _zz_682 = ($signed(_zz_131) - $signed(_zz_683));
  assign _zz_683 = ($signed(_zz_684) * $signed(k_imag));
  assign _zz_684 = ($signed(deltax_real) + $signed(deltax_imag));
  assign _zz_685 = ($signed(_zz_131) + $signed(_zz_686));
  assign _zz_686 = ($signed(_zz_687) * $signed(k_real));
  assign _zz_687 = ($signed(deltax_imag) - $signed(deltax_real));
  assign _zz_688 = _zz_689[63 : 0];
  assign _zz_689 = fixTo_10_dout;
  assign _zz_690 = ({16'd0,y1_real} <<< 16);
  assign _zz_691 = {{16{_zz_690[47]}}, _zz_690};
  assign _zz_692 = _zz_693[63 : 0];
  assign _zz_693 = fixTo_11_dout;
  assign _zz_694 = ({16'd0,y1_imag} <<< 16);
  assign _zz_695 = {{16{_zz_694[47]}}, _zz_694};
  assign _zz_696 = fixTo_12_dout;
  assign _zz_697 = fixTo_13_dout;
  SInt32fixTo31_0_ROUNDTOINF fixTo (
    .din     (io_data_in[31:0]  ), //i
    .dout    (fixTo_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_1 (
    .din     (_zz_132[31:0]       ), //i
    .dout    (fixTo_1_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_2 (
    .din     (_zz_133[31:0]       ), //i
    .dout    (fixTo_2_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_3 (
    .din     (_zz_134[31:0]       ), //i
    .dout    (fixTo_3_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_4 (
    .din     (_zz_135[31:0]       ), //i
    .dout    (fixTo_4_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_5 (
    .din     (_zz_136[31:0]       ), //i
    .dout    (fixTo_5_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_6 (
    .din     (_zz_137[63:0]       ), //i
    .dout    (fixTo_6_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_7 (
    .din     (_zz_138[31:0]       ), //i
    .dout    (fixTo_7_dout[31:0]  )  //o
  );
  SInt32fixTo31_0_ROUNDTOINF fixTo_8 (
    .din     (_zz_139[31:0]       ), //i
    .dout    (fixTo_8_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_9 (
    .din     (_zz_140[63:0]       ), //i
    .dout    (fixTo_9_dout[31:0]  )  //o
  );
  SInt64fixTo63_0_ROUNDTOINF fixTo_10 (
    .din     (_zz_141[63:0]        ), //i
    .dout    (fixTo_10_dout[63:0]  )  //o
  );
  SInt64fixTo63_0_ROUNDTOINF fixTo_11 (
    .din     (_zz_142[63:0]        ), //i
    .dout    (fixTo_11_dout[63:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_12 (
    .din     (_zz_143[63:0]        ), //i
    .dout    (fixTo_12_dout[31:0]  )  //o
  );
  SInt64fixTo47_16_ROUNDTOINF fixTo_13 (
    .din     (_zz_144[63:0]        ), //i
    .dout    (fixTo_13_dout[31:0]  )  //o
  );
  always @(*) begin
    case(position)
      7'b0000000 : begin
        _zz_145 = pindx_tb_0;
        _zz_146 = pfunc_tb_0_real;
        _zz_147 = pfunc_tb_0_imag;
      end
      7'b0000001 : begin
        _zz_145 = pindx_tb_1;
        _zz_146 = pfunc_tb_1_real;
        _zz_147 = pfunc_tb_1_imag;
      end
      7'b0000010 : begin
        _zz_145 = pindx_tb_2;
        _zz_146 = pfunc_tb_2_real;
        _zz_147 = pfunc_tb_2_imag;
      end
      7'b0000011 : begin
        _zz_145 = pindx_tb_3;
        _zz_146 = pfunc_tb_3_real;
        _zz_147 = pfunc_tb_3_imag;
      end
      7'b0000100 : begin
        _zz_145 = pindx_tb_4;
        _zz_146 = pfunc_tb_4_real;
        _zz_147 = pfunc_tb_4_imag;
      end
      7'b0000101 : begin
        _zz_145 = pindx_tb_5;
        _zz_146 = pfunc_tb_5_real;
        _zz_147 = pfunc_tb_5_imag;
      end
      7'b0000110 : begin
        _zz_145 = pindx_tb_6;
        _zz_146 = pfunc_tb_6_real;
        _zz_147 = pfunc_tb_6_imag;
      end
      7'b0000111 : begin
        _zz_145 = pindx_tb_7;
        _zz_146 = pfunc_tb_7_real;
        _zz_147 = pfunc_tb_7_imag;
      end
      7'b0001000 : begin
        _zz_145 = pindx_tb_8;
        _zz_146 = pfunc_tb_8_real;
        _zz_147 = pfunc_tb_8_imag;
      end
      7'b0001001 : begin
        _zz_145 = pindx_tb_9;
        _zz_146 = pfunc_tb_9_real;
        _zz_147 = pfunc_tb_9_imag;
      end
      7'b0001010 : begin
        _zz_145 = pindx_tb_10;
        _zz_146 = pfunc_tb_10_real;
        _zz_147 = pfunc_tb_10_imag;
      end
      7'b0001011 : begin
        _zz_145 = pindx_tb_11;
        _zz_146 = pfunc_tb_11_real;
        _zz_147 = pfunc_tb_11_imag;
      end
      7'b0001100 : begin
        _zz_145 = pindx_tb_12;
        _zz_146 = pfunc_tb_12_real;
        _zz_147 = pfunc_tb_12_imag;
      end
      7'b0001101 : begin
        _zz_145 = pindx_tb_13;
        _zz_146 = pfunc_tb_13_real;
        _zz_147 = pfunc_tb_13_imag;
      end
      7'b0001110 : begin
        _zz_145 = pindx_tb_14;
        _zz_146 = pfunc_tb_14_real;
        _zz_147 = pfunc_tb_14_imag;
      end
      7'b0001111 : begin
        _zz_145 = pindx_tb_15;
        _zz_146 = pfunc_tb_15_real;
        _zz_147 = pfunc_tb_15_imag;
      end
      7'b0010000 : begin
        _zz_145 = pindx_tb_16;
        _zz_146 = pfunc_tb_16_real;
        _zz_147 = pfunc_tb_16_imag;
      end
      7'b0010001 : begin
        _zz_145 = pindx_tb_17;
        _zz_146 = pfunc_tb_17_real;
        _zz_147 = pfunc_tb_17_imag;
      end
      7'b0010010 : begin
        _zz_145 = pindx_tb_18;
        _zz_146 = pfunc_tb_18_real;
        _zz_147 = pfunc_tb_18_imag;
      end
      7'b0010011 : begin
        _zz_145 = pindx_tb_19;
        _zz_146 = pfunc_tb_19_real;
        _zz_147 = pfunc_tb_19_imag;
      end
      7'b0010100 : begin
        _zz_145 = pindx_tb_20;
        _zz_146 = pfunc_tb_20_real;
        _zz_147 = pfunc_tb_20_imag;
      end
      7'b0010101 : begin
        _zz_145 = pindx_tb_21;
        _zz_146 = pfunc_tb_21_real;
        _zz_147 = pfunc_tb_21_imag;
      end
      7'b0010110 : begin
        _zz_145 = pindx_tb_22;
        _zz_146 = pfunc_tb_22_real;
        _zz_147 = pfunc_tb_22_imag;
      end
      7'b0010111 : begin
        _zz_145 = pindx_tb_23;
        _zz_146 = pfunc_tb_23_real;
        _zz_147 = pfunc_tb_23_imag;
      end
      7'b0011000 : begin
        _zz_145 = pindx_tb_24;
        _zz_146 = pfunc_tb_24_real;
        _zz_147 = pfunc_tb_24_imag;
      end
      7'b0011001 : begin
        _zz_145 = pindx_tb_25;
        _zz_146 = pfunc_tb_25_real;
        _zz_147 = pfunc_tb_25_imag;
      end
      7'b0011010 : begin
        _zz_145 = pindx_tb_26;
        _zz_146 = pfunc_tb_26_real;
        _zz_147 = pfunc_tb_26_imag;
      end
      7'b0011011 : begin
        _zz_145 = pindx_tb_27;
        _zz_146 = pfunc_tb_27_real;
        _zz_147 = pfunc_tb_27_imag;
      end
      7'b0011100 : begin
        _zz_145 = pindx_tb_28;
        _zz_146 = pfunc_tb_28_real;
        _zz_147 = pfunc_tb_28_imag;
      end
      7'b0011101 : begin
        _zz_145 = pindx_tb_29;
        _zz_146 = pfunc_tb_29_real;
        _zz_147 = pfunc_tb_29_imag;
      end
      7'b0011110 : begin
        _zz_145 = pindx_tb_30;
        _zz_146 = pfunc_tb_30_real;
        _zz_147 = pfunc_tb_30_imag;
      end
      7'b0011111 : begin
        _zz_145 = pindx_tb_31;
        _zz_146 = pfunc_tb_31_real;
        _zz_147 = pfunc_tb_31_imag;
      end
      7'b0100000 : begin
        _zz_145 = pindx_tb_32;
        _zz_146 = pfunc_tb_32_real;
        _zz_147 = pfunc_tb_32_imag;
      end
      7'b0100001 : begin
        _zz_145 = pindx_tb_33;
        _zz_146 = pfunc_tb_33_real;
        _zz_147 = pfunc_tb_33_imag;
      end
      7'b0100010 : begin
        _zz_145 = pindx_tb_34;
        _zz_146 = pfunc_tb_34_real;
        _zz_147 = pfunc_tb_34_imag;
      end
      7'b0100011 : begin
        _zz_145 = pindx_tb_35;
        _zz_146 = pfunc_tb_35_real;
        _zz_147 = pfunc_tb_35_imag;
      end
      7'b0100100 : begin
        _zz_145 = pindx_tb_36;
        _zz_146 = pfunc_tb_36_real;
        _zz_147 = pfunc_tb_36_imag;
      end
      7'b0100101 : begin
        _zz_145 = pindx_tb_37;
        _zz_146 = pfunc_tb_37_real;
        _zz_147 = pfunc_tb_37_imag;
      end
      7'b0100110 : begin
        _zz_145 = pindx_tb_38;
        _zz_146 = pfunc_tb_38_real;
        _zz_147 = pfunc_tb_38_imag;
      end
      7'b0100111 : begin
        _zz_145 = pindx_tb_39;
        _zz_146 = pfunc_tb_39_real;
        _zz_147 = pfunc_tb_39_imag;
      end
      7'b0101000 : begin
        _zz_145 = pindx_tb_40;
        _zz_146 = pfunc_tb_40_real;
        _zz_147 = pfunc_tb_40_imag;
      end
      7'b0101001 : begin
        _zz_145 = pindx_tb_41;
        _zz_146 = pfunc_tb_41_real;
        _zz_147 = pfunc_tb_41_imag;
      end
      7'b0101010 : begin
        _zz_145 = pindx_tb_42;
        _zz_146 = pfunc_tb_42_real;
        _zz_147 = pfunc_tb_42_imag;
      end
      7'b0101011 : begin
        _zz_145 = pindx_tb_43;
        _zz_146 = pfunc_tb_43_real;
        _zz_147 = pfunc_tb_43_imag;
      end
      7'b0101100 : begin
        _zz_145 = pindx_tb_44;
        _zz_146 = pfunc_tb_44_real;
        _zz_147 = pfunc_tb_44_imag;
      end
      7'b0101101 : begin
        _zz_145 = pindx_tb_45;
        _zz_146 = pfunc_tb_45_real;
        _zz_147 = pfunc_tb_45_imag;
      end
      7'b0101110 : begin
        _zz_145 = pindx_tb_46;
        _zz_146 = pfunc_tb_46_real;
        _zz_147 = pfunc_tb_46_imag;
      end
      7'b0101111 : begin
        _zz_145 = pindx_tb_47;
        _zz_146 = pfunc_tb_47_real;
        _zz_147 = pfunc_tb_47_imag;
      end
      7'b0110000 : begin
        _zz_145 = pindx_tb_48;
        _zz_146 = pfunc_tb_48_real;
        _zz_147 = pfunc_tb_48_imag;
      end
      7'b0110001 : begin
        _zz_145 = pindx_tb_49;
        _zz_146 = pfunc_tb_49_real;
        _zz_147 = pfunc_tb_49_imag;
      end
      7'b0110010 : begin
        _zz_145 = pindx_tb_50;
        _zz_146 = pfunc_tb_50_real;
        _zz_147 = pfunc_tb_50_imag;
      end
      7'b0110011 : begin
        _zz_145 = pindx_tb_51;
        _zz_146 = pfunc_tb_51_real;
        _zz_147 = pfunc_tb_51_imag;
      end
      7'b0110100 : begin
        _zz_145 = pindx_tb_52;
        _zz_146 = pfunc_tb_52_real;
        _zz_147 = pfunc_tb_52_imag;
      end
      7'b0110101 : begin
        _zz_145 = pindx_tb_53;
        _zz_146 = pfunc_tb_53_real;
        _zz_147 = pfunc_tb_53_imag;
      end
      7'b0110110 : begin
        _zz_145 = pindx_tb_54;
        _zz_146 = pfunc_tb_54_real;
        _zz_147 = pfunc_tb_54_imag;
      end
      7'b0110111 : begin
        _zz_145 = pindx_tb_55;
        _zz_146 = pfunc_tb_55_real;
        _zz_147 = pfunc_tb_55_imag;
      end
      7'b0111000 : begin
        _zz_145 = pindx_tb_56;
        _zz_146 = pfunc_tb_56_real;
        _zz_147 = pfunc_tb_56_imag;
      end
      7'b0111001 : begin
        _zz_145 = pindx_tb_57;
        _zz_146 = pfunc_tb_57_real;
        _zz_147 = pfunc_tb_57_imag;
      end
      7'b0111010 : begin
        _zz_145 = pindx_tb_58;
        _zz_146 = pfunc_tb_58_real;
        _zz_147 = pfunc_tb_58_imag;
      end
      7'b0111011 : begin
        _zz_145 = pindx_tb_59;
        _zz_146 = pfunc_tb_59_real;
        _zz_147 = pfunc_tb_59_imag;
      end
      7'b0111100 : begin
        _zz_145 = pindx_tb_60;
        _zz_146 = pfunc_tb_60_real;
        _zz_147 = pfunc_tb_60_imag;
      end
      7'b0111101 : begin
        _zz_145 = pindx_tb_61;
        _zz_146 = pfunc_tb_61_real;
        _zz_147 = pfunc_tb_61_imag;
      end
      7'b0111110 : begin
        _zz_145 = pindx_tb_62;
        _zz_146 = pfunc_tb_62_real;
        _zz_147 = pfunc_tb_62_imag;
      end
      7'b0111111 : begin
        _zz_145 = pindx_tb_63;
        _zz_146 = pfunc_tb_63_real;
        _zz_147 = pfunc_tb_63_imag;
      end
      7'b1000000 : begin
        _zz_145 = pindx_tb_64;
        _zz_146 = pfunc_tb_64_real;
        _zz_147 = pfunc_tb_64_imag;
      end
      7'b1000001 : begin
        _zz_145 = pindx_tb_65;
        _zz_146 = pfunc_tb_65_real;
        _zz_147 = pfunc_tb_65_imag;
      end
      7'b1000010 : begin
        _zz_145 = pindx_tb_66;
        _zz_146 = pfunc_tb_66_real;
        _zz_147 = pfunc_tb_66_imag;
      end
      7'b1000011 : begin
        _zz_145 = pindx_tb_67;
        _zz_146 = pfunc_tb_67_real;
        _zz_147 = pfunc_tb_67_imag;
      end
      7'b1000100 : begin
        _zz_145 = pindx_tb_68;
        _zz_146 = pfunc_tb_68_real;
        _zz_147 = pfunc_tb_68_imag;
      end
      7'b1000101 : begin
        _zz_145 = pindx_tb_69;
        _zz_146 = pfunc_tb_69_real;
        _zz_147 = pfunc_tb_69_imag;
      end
      7'b1000110 : begin
        _zz_145 = pindx_tb_70;
        _zz_146 = pfunc_tb_70_real;
        _zz_147 = pfunc_tb_70_imag;
      end
      7'b1000111 : begin
        _zz_145 = pindx_tb_71;
        _zz_146 = pfunc_tb_71_real;
        _zz_147 = pfunc_tb_71_imag;
      end
      7'b1001000 : begin
        _zz_145 = pindx_tb_72;
        _zz_146 = pfunc_tb_72_real;
        _zz_147 = pfunc_tb_72_imag;
      end
      7'b1001001 : begin
        _zz_145 = pindx_tb_73;
        _zz_146 = pfunc_tb_73_real;
        _zz_147 = pfunc_tb_73_imag;
      end
      7'b1001010 : begin
        _zz_145 = pindx_tb_74;
        _zz_146 = pfunc_tb_74_real;
        _zz_147 = pfunc_tb_74_imag;
      end
      7'b1001011 : begin
        _zz_145 = pindx_tb_75;
        _zz_146 = pfunc_tb_75_real;
        _zz_147 = pfunc_tb_75_imag;
      end
      7'b1001100 : begin
        _zz_145 = pindx_tb_76;
        _zz_146 = pfunc_tb_76_real;
        _zz_147 = pfunc_tb_76_imag;
      end
      7'b1001101 : begin
        _zz_145 = pindx_tb_77;
        _zz_146 = pfunc_tb_77_real;
        _zz_147 = pfunc_tb_77_imag;
      end
      7'b1001110 : begin
        _zz_145 = pindx_tb_78;
        _zz_146 = pfunc_tb_78_real;
        _zz_147 = pfunc_tb_78_imag;
      end
      7'b1001111 : begin
        _zz_145 = pindx_tb_79;
        _zz_146 = pfunc_tb_79_real;
        _zz_147 = pfunc_tb_79_imag;
      end
      7'b1010000 : begin
        _zz_145 = pindx_tb_80;
        _zz_146 = pfunc_tb_80_real;
        _zz_147 = pfunc_tb_80_imag;
      end
      7'b1010001 : begin
        _zz_145 = pindx_tb_81;
        _zz_146 = pfunc_tb_81_real;
        _zz_147 = pfunc_tb_81_imag;
      end
      7'b1010010 : begin
        _zz_145 = pindx_tb_82;
        _zz_146 = pfunc_tb_82_real;
        _zz_147 = pfunc_tb_82_imag;
      end
      7'b1010011 : begin
        _zz_145 = pindx_tb_83;
        _zz_146 = pfunc_tb_83_real;
        _zz_147 = pfunc_tb_83_imag;
      end
      7'b1010100 : begin
        _zz_145 = pindx_tb_84;
        _zz_146 = pfunc_tb_84_real;
        _zz_147 = pfunc_tb_84_imag;
      end
      7'b1010101 : begin
        _zz_145 = pindx_tb_85;
        _zz_146 = pfunc_tb_85_real;
        _zz_147 = pfunc_tb_85_imag;
      end
      7'b1010110 : begin
        _zz_145 = pindx_tb_86;
        _zz_146 = pfunc_tb_86_real;
        _zz_147 = pfunc_tb_86_imag;
      end
      7'b1010111 : begin
        _zz_145 = pindx_tb_87;
        _zz_146 = pfunc_tb_87_real;
        _zz_147 = pfunc_tb_87_imag;
      end
      7'b1011000 : begin
        _zz_145 = pindx_tb_88;
        _zz_146 = pfunc_tb_88_real;
        _zz_147 = pfunc_tb_88_imag;
      end
      7'b1011001 : begin
        _zz_145 = pindx_tb_89;
        _zz_146 = pfunc_tb_89_real;
        _zz_147 = pfunc_tb_89_imag;
      end
      7'b1011010 : begin
        _zz_145 = pindx_tb_90;
        _zz_146 = pfunc_tb_90_real;
        _zz_147 = pfunc_tb_90_imag;
      end
      7'b1011011 : begin
        _zz_145 = pindx_tb_91;
        _zz_146 = pfunc_tb_91_real;
        _zz_147 = pfunc_tb_91_imag;
      end
      7'b1011100 : begin
        _zz_145 = pindx_tb_92;
        _zz_146 = pfunc_tb_92_real;
        _zz_147 = pfunc_tb_92_imag;
      end
      7'b1011101 : begin
        _zz_145 = pindx_tb_93;
        _zz_146 = pfunc_tb_93_real;
        _zz_147 = pfunc_tb_93_imag;
      end
      7'b1011110 : begin
        _zz_145 = pindx_tb_94;
        _zz_146 = pfunc_tb_94_real;
        _zz_147 = pfunc_tb_94_imag;
      end
      7'b1011111 : begin
        _zz_145 = pindx_tb_95;
        _zz_146 = pfunc_tb_95_real;
        _zz_147 = pfunc_tb_95_imag;
      end
      7'b1100000 : begin
        _zz_145 = pindx_tb_96;
        _zz_146 = pfunc_tb_96_real;
        _zz_147 = pfunc_tb_96_imag;
      end
      7'b1100001 : begin
        _zz_145 = pindx_tb_97;
        _zz_146 = pfunc_tb_97_real;
        _zz_147 = pfunc_tb_97_imag;
      end
      7'b1100010 : begin
        _zz_145 = pindx_tb_98;
        _zz_146 = pfunc_tb_98_real;
        _zz_147 = pfunc_tb_98_imag;
      end
      7'b1100011 : begin
        _zz_145 = pindx_tb_99;
        _zz_146 = pfunc_tb_99_real;
        _zz_147 = pfunc_tb_99_imag;
      end
      7'b1100100 : begin
        _zz_145 = pindx_tb_100;
        _zz_146 = pfunc_tb_100_real;
        _zz_147 = pfunc_tb_100_imag;
      end
      7'b1100101 : begin
        _zz_145 = pindx_tb_101;
        _zz_146 = pfunc_tb_101_real;
        _zz_147 = pfunc_tb_101_imag;
      end
      7'b1100110 : begin
        _zz_145 = pindx_tb_102;
        _zz_146 = pfunc_tb_102_real;
        _zz_147 = pfunc_tb_102_imag;
      end
      7'b1100111 : begin
        _zz_145 = pindx_tb_103;
        _zz_146 = pfunc_tb_103_real;
        _zz_147 = pfunc_tb_103_imag;
      end
      7'b1101000 : begin
        _zz_145 = pindx_tb_104;
        _zz_146 = pfunc_tb_104_real;
        _zz_147 = pfunc_tb_104_imag;
      end
      7'b1101001 : begin
        _zz_145 = pindx_tb_105;
        _zz_146 = pfunc_tb_105_real;
        _zz_147 = pfunc_tb_105_imag;
      end
      7'b1101010 : begin
        _zz_145 = pindx_tb_106;
        _zz_146 = pfunc_tb_106_real;
        _zz_147 = pfunc_tb_106_imag;
      end
      7'b1101011 : begin
        _zz_145 = pindx_tb_107;
        _zz_146 = pfunc_tb_107_real;
        _zz_147 = pfunc_tb_107_imag;
      end
      7'b1101100 : begin
        _zz_145 = pindx_tb_108;
        _zz_146 = pfunc_tb_108_real;
        _zz_147 = pfunc_tb_108_imag;
      end
      7'b1101101 : begin
        _zz_145 = pindx_tb_109;
        _zz_146 = pfunc_tb_109_real;
        _zz_147 = pfunc_tb_109_imag;
      end
      7'b1101110 : begin
        _zz_145 = pindx_tb_110;
        _zz_146 = pfunc_tb_110_real;
        _zz_147 = pfunc_tb_110_imag;
      end
      7'b1101111 : begin
        _zz_145 = pindx_tb_111;
        _zz_146 = pfunc_tb_111_real;
        _zz_147 = pfunc_tb_111_imag;
      end
      7'b1110000 : begin
        _zz_145 = pindx_tb_112;
        _zz_146 = pfunc_tb_112_real;
        _zz_147 = pfunc_tb_112_imag;
      end
      7'b1110001 : begin
        _zz_145 = pindx_tb_113;
        _zz_146 = pfunc_tb_113_real;
        _zz_147 = pfunc_tb_113_imag;
      end
      7'b1110010 : begin
        _zz_145 = pindx_tb_114;
        _zz_146 = pfunc_tb_114_real;
        _zz_147 = pfunc_tb_114_imag;
      end
      7'b1110011 : begin
        _zz_145 = pindx_tb_115;
        _zz_146 = pfunc_tb_115_real;
        _zz_147 = pfunc_tb_115_imag;
      end
      7'b1110100 : begin
        _zz_145 = pindx_tb_116;
        _zz_146 = pfunc_tb_116_real;
        _zz_147 = pfunc_tb_116_imag;
      end
      7'b1110101 : begin
        _zz_145 = pindx_tb_117;
        _zz_146 = pfunc_tb_117_real;
        _zz_147 = pfunc_tb_117_imag;
      end
      7'b1110110 : begin
        _zz_145 = pindx_tb_118;
        _zz_146 = pfunc_tb_118_real;
        _zz_147 = pfunc_tb_118_imag;
      end
      7'b1110111 : begin
        _zz_145 = pindx_tb_119;
        _zz_146 = pfunc_tb_119_real;
        _zz_147 = pfunc_tb_119_imag;
      end
      7'b1111000 : begin
        _zz_145 = pindx_tb_120;
        _zz_146 = pfunc_tb_120_real;
        _zz_147 = pfunc_tb_120_imag;
      end
      7'b1111001 : begin
        _zz_145 = pindx_tb_121;
        _zz_146 = pfunc_tb_121_real;
        _zz_147 = pfunc_tb_121_imag;
      end
      7'b1111010 : begin
        _zz_145 = pindx_tb_122;
        _zz_146 = pfunc_tb_122_real;
        _zz_147 = pfunc_tb_122_imag;
      end
      7'b1111011 : begin
        _zz_145 = pindx_tb_123;
        _zz_146 = pfunc_tb_123_real;
        _zz_147 = pfunc_tb_123_imag;
      end
      7'b1111100 : begin
        _zz_145 = pindx_tb_124;
        _zz_146 = pfunc_tb_124_real;
        _zz_147 = pfunc_tb_124_imag;
      end
      7'b1111101 : begin
        _zz_145 = pindx_tb_125;
        _zz_146 = pfunc_tb_125_real;
        _zz_147 = pfunc_tb_125_imag;
      end
      7'b1111110 : begin
        _zz_145 = pindx_tb_126;
        _zz_146 = pfunc_tb_126_real;
        _zz_147 = pfunc_tb_126_imag;
      end
      default : begin
        _zz_145 = pindx_tb_127;
        _zz_146 = pfunc_tb_127_real;
        _zz_147 = pfunc_tb_127_imag;
      end
    endcase
  end

  always @(*) begin
    case(lzc)
      7'b0000000 : begin
        _zz_148 = pindx_tb_0;
        _zz_149 = pfunc_tb_0_real;
        _zz_150 = pfunc_tb_0_imag;
      end
      7'b0000001 : begin
        _zz_148 = pindx_tb_1;
        _zz_149 = pfunc_tb_1_real;
        _zz_150 = pfunc_tb_1_imag;
      end
      7'b0000010 : begin
        _zz_148 = pindx_tb_2;
        _zz_149 = pfunc_tb_2_real;
        _zz_150 = pfunc_tb_2_imag;
      end
      7'b0000011 : begin
        _zz_148 = pindx_tb_3;
        _zz_149 = pfunc_tb_3_real;
        _zz_150 = pfunc_tb_3_imag;
      end
      7'b0000100 : begin
        _zz_148 = pindx_tb_4;
        _zz_149 = pfunc_tb_4_real;
        _zz_150 = pfunc_tb_4_imag;
      end
      7'b0000101 : begin
        _zz_148 = pindx_tb_5;
        _zz_149 = pfunc_tb_5_real;
        _zz_150 = pfunc_tb_5_imag;
      end
      7'b0000110 : begin
        _zz_148 = pindx_tb_6;
        _zz_149 = pfunc_tb_6_real;
        _zz_150 = pfunc_tb_6_imag;
      end
      7'b0000111 : begin
        _zz_148 = pindx_tb_7;
        _zz_149 = pfunc_tb_7_real;
        _zz_150 = pfunc_tb_7_imag;
      end
      7'b0001000 : begin
        _zz_148 = pindx_tb_8;
        _zz_149 = pfunc_tb_8_real;
        _zz_150 = pfunc_tb_8_imag;
      end
      7'b0001001 : begin
        _zz_148 = pindx_tb_9;
        _zz_149 = pfunc_tb_9_real;
        _zz_150 = pfunc_tb_9_imag;
      end
      7'b0001010 : begin
        _zz_148 = pindx_tb_10;
        _zz_149 = pfunc_tb_10_real;
        _zz_150 = pfunc_tb_10_imag;
      end
      7'b0001011 : begin
        _zz_148 = pindx_tb_11;
        _zz_149 = pfunc_tb_11_real;
        _zz_150 = pfunc_tb_11_imag;
      end
      7'b0001100 : begin
        _zz_148 = pindx_tb_12;
        _zz_149 = pfunc_tb_12_real;
        _zz_150 = pfunc_tb_12_imag;
      end
      7'b0001101 : begin
        _zz_148 = pindx_tb_13;
        _zz_149 = pfunc_tb_13_real;
        _zz_150 = pfunc_tb_13_imag;
      end
      7'b0001110 : begin
        _zz_148 = pindx_tb_14;
        _zz_149 = pfunc_tb_14_real;
        _zz_150 = pfunc_tb_14_imag;
      end
      7'b0001111 : begin
        _zz_148 = pindx_tb_15;
        _zz_149 = pfunc_tb_15_real;
        _zz_150 = pfunc_tb_15_imag;
      end
      7'b0010000 : begin
        _zz_148 = pindx_tb_16;
        _zz_149 = pfunc_tb_16_real;
        _zz_150 = pfunc_tb_16_imag;
      end
      7'b0010001 : begin
        _zz_148 = pindx_tb_17;
        _zz_149 = pfunc_tb_17_real;
        _zz_150 = pfunc_tb_17_imag;
      end
      7'b0010010 : begin
        _zz_148 = pindx_tb_18;
        _zz_149 = pfunc_tb_18_real;
        _zz_150 = pfunc_tb_18_imag;
      end
      7'b0010011 : begin
        _zz_148 = pindx_tb_19;
        _zz_149 = pfunc_tb_19_real;
        _zz_150 = pfunc_tb_19_imag;
      end
      7'b0010100 : begin
        _zz_148 = pindx_tb_20;
        _zz_149 = pfunc_tb_20_real;
        _zz_150 = pfunc_tb_20_imag;
      end
      7'b0010101 : begin
        _zz_148 = pindx_tb_21;
        _zz_149 = pfunc_tb_21_real;
        _zz_150 = pfunc_tb_21_imag;
      end
      7'b0010110 : begin
        _zz_148 = pindx_tb_22;
        _zz_149 = pfunc_tb_22_real;
        _zz_150 = pfunc_tb_22_imag;
      end
      7'b0010111 : begin
        _zz_148 = pindx_tb_23;
        _zz_149 = pfunc_tb_23_real;
        _zz_150 = pfunc_tb_23_imag;
      end
      7'b0011000 : begin
        _zz_148 = pindx_tb_24;
        _zz_149 = pfunc_tb_24_real;
        _zz_150 = pfunc_tb_24_imag;
      end
      7'b0011001 : begin
        _zz_148 = pindx_tb_25;
        _zz_149 = pfunc_tb_25_real;
        _zz_150 = pfunc_tb_25_imag;
      end
      7'b0011010 : begin
        _zz_148 = pindx_tb_26;
        _zz_149 = pfunc_tb_26_real;
        _zz_150 = pfunc_tb_26_imag;
      end
      7'b0011011 : begin
        _zz_148 = pindx_tb_27;
        _zz_149 = pfunc_tb_27_real;
        _zz_150 = pfunc_tb_27_imag;
      end
      7'b0011100 : begin
        _zz_148 = pindx_tb_28;
        _zz_149 = pfunc_tb_28_real;
        _zz_150 = pfunc_tb_28_imag;
      end
      7'b0011101 : begin
        _zz_148 = pindx_tb_29;
        _zz_149 = pfunc_tb_29_real;
        _zz_150 = pfunc_tb_29_imag;
      end
      7'b0011110 : begin
        _zz_148 = pindx_tb_30;
        _zz_149 = pfunc_tb_30_real;
        _zz_150 = pfunc_tb_30_imag;
      end
      7'b0011111 : begin
        _zz_148 = pindx_tb_31;
        _zz_149 = pfunc_tb_31_real;
        _zz_150 = pfunc_tb_31_imag;
      end
      7'b0100000 : begin
        _zz_148 = pindx_tb_32;
        _zz_149 = pfunc_tb_32_real;
        _zz_150 = pfunc_tb_32_imag;
      end
      7'b0100001 : begin
        _zz_148 = pindx_tb_33;
        _zz_149 = pfunc_tb_33_real;
        _zz_150 = pfunc_tb_33_imag;
      end
      7'b0100010 : begin
        _zz_148 = pindx_tb_34;
        _zz_149 = pfunc_tb_34_real;
        _zz_150 = pfunc_tb_34_imag;
      end
      7'b0100011 : begin
        _zz_148 = pindx_tb_35;
        _zz_149 = pfunc_tb_35_real;
        _zz_150 = pfunc_tb_35_imag;
      end
      7'b0100100 : begin
        _zz_148 = pindx_tb_36;
        _zz_149 = pfunc_tb_36_real;
        _zz_150 = pfunc_tb_36_imag;
      end
      7'b0100101 : begin
        _zz_148 = pindx_tb_37;
        _zz_149 = pfunc_tb_37_real;
        _zz_150 = pfunc_tb_37_imag;
      end
      7'b0100110 : begin
        _zz_148 = pindx_tb_38;
        _zz_149 = pfunc_tb_38_real;
        _zz_150 = pfunc_tb_38_imag;
      end
      7'b0100111 : begin
        _zz_148 = pindx_tb_39;
        _zz_149 = pfunc_tb_39_real;
        _zz_150 = pfunc_tb_39_imag;
      end
      7'b0101000 : begin
        _zz_148 = pindx_tb_40;
        _zz_149 = pfunc_tb_40_real;
        _zz_150 = pfunc_tb_40_imag;
      end
      7'b0101001 : begin
        _zz_148 = pindx_tb_41;
        _zz_149 = pfunc_tb_41_real;
        _zz_150 = pfunc_tb_41_imag;
      end
      7'b0101010 : begin
        _zz_148 = pindx_tb_42;
        _zz_149 = pfunc_tb_42_real;
        _zz_150 = pfunc_tb_42_imag;
      end
      7'b0101011 : begin
        _zz_148 = pindx_tb_43;
        _zz_149 = pfunc_tb_43_real;
        _zz_150 = pfunc_tb_43_imag;
      end
      7'b0101100 : begin
        _zz_148 = pindx_tb_44;
        _zz_149 = pfunc_tb_44_real;
        _zz_150 = pfunc_tb_44_imag;
      end
      7'b0101101 : begin
        _zz_148 = pindx_tb_45;
        _zz_149 = pfunc_tb_45_real;
        _zz_150 = pfunc_tb_45_imag;
      end
      7'b0101110 : begin
        _zz_148 = pindx_tb_46;
        _zz_149 = pfunc_tb_46_real;
        _zz_150 = pfunc_tb_46_imag;
      end
      7'b0101111 : begin
        _zz_148 = pindx_tb_47;
        _zz_149 = pfunc_tb_47_real;
        _zz_150 = pfunc_tb_47_imag;
      end
      7'b0110000 : begin
        _zz_148 = pindx_tb_48;
        _zz_149 = pfunc_tb_48_real;
        _zz_150 = pfunc_tb_48_imag;
      end
      7'b0110001 : begin
        _zz_148 = pindx_tb_49;
        _zz_149 = pfunc_tb_49_real;
        _zz_150 = pfunc_tb_49_imag;
      end
      7'b0110010 : begin
        _zz_148 = pindx_tb_50;
        _zz_149 = pfunc_tb_50_real;
        _zz_150 = pfunc_tb_50_imag;
      end
      7'b0110011 : begin
        _zz_148 = pindx_tb_51;
        _zz_149 = pfunc_tb_51_real;
        _zz_150 = pfunc_tb_51_imag;
      end
      7'b0110100 : begin
        _zz_148 = pindx_tb_52;
        _zz_149 = pfunc_tb_52_real;
        _zz_150 = pfunc_tb_52_imag;
      end
      7'b0110101 : begin
        _zz_148 = pindx_tb_53;
        _zz_149 = pfunc_tb_53_real;
        _zz_150 = pfunc_tb_53_imag;
      end
      7'b0110110 : begin
        _zz_148 = pindx_tb_54;
        _zz_149 = pfunc_tb_54_real;
        _zz_150 = pfunc_tb_54_imag;
      end
      7'b0110111 : begin
        _zz_148 = pindx_tb_55;
        _zz_149 = pfunc_tb_55_real;
        _zz_150 = pfunc_tb_55_imag;
      end
      7'b0111000 : begin
        _zz_148 = pindx_tb_56;
        _zz_149 = pfunc_tb_56_real;
        _zz_150 = pfunc_tb_56_imag;
      end
      7'b0111001 : begin
        _zz_148 = pindx_tb_57;
        _zz_149 = pfunc_tb_57_real;
        _zz_150 = pfunc_tb_57_imag;
      end
      7'b0111010 : begin
        _zz_148 = pindx_tb_58;
        _zz_149 = pfunc_tb_58_real;
        _zz_150 = pfunc_tb_58_imag;
      end
      7'b0111011 : begin
        _zz_148 = pindx_tb_59;
        _zz_149 = pfunc_tb_59_real;
        _zz_150 = pfunc_tb_59_imag;
      end
      7'b0111100 : begin
        _zz_148 = pindx_tb_60;
        _zz_149 = pfunc_tb_60_real;
        _zz_150 = pfunc_tb_60_imag;
      end
      7'b0111101 : begin
        _zz_148 = pindx_tb_61;
        _zz_149 = pfunc_tb_61_real;
        _zz_150 = pfunc_tb_61_imag;
      end
      7'b0111110 : begin
        _zz_148 = pindx_tb_62;
        _zz_149 = pfunc_tb_62_real;
        _zz_150 = pfunc_tb_62_imag;
      end
      7'b0111111 : begin
        _zz_148 = pindx_tb_63;
        _zz_149 = pfunc_tb_63_real;
        _zz_150 = pfunc_tb_63_imag;
      end
      7'b1000000 : begin
        _zz_148 = pindx_tb_64;
        _zz_149 = pfunc_tb_64_real;
        _zz_150 = pfunc_tb_64_imag;
      end
      7'b1000001 : begin
        _zz_148 = pindx_tb_65;
        _zz_149 = pfunc_tb_65_real;
        _zz_150 = pfunc_tb_65_imag;
      end
      7'b1000010 : begin
        _zz_148 = pindx_tb_66;
        _zz_149 = pfunc_tb_66_real;
        _zz_150 = pfunc_tb_66_imag;
      end
      7'b1000011 : begin
        _zz_148 = pindx_tb_67;
        _zz_149 = pfunc_tb_67_real;
        _zz_150 = pfunc_tb_67_imag;
      end
      7'b1000100 : begin
        _zz_148 = pindx_tb_68;
        _zz_149 = pfunc_tb_68_real;
        _zz_150 = pfunc_tb_68_imag;
      end
      7'b1000101 : begin
        _zz_148 = pindx_tb_69;
        _zz_149 = pfunc_tb_69_real;
        _zz_150 = pfunc_tb_69_imag;
      end
      7'b1000110 : begin
        _zz_148 = pindx_tb_70;
        _zz_149 = pfunc_tb_70_real;
        _zz_150 = pfunc_tb_70_imag;
      end
      7'b1000111 : begin
        _zz_148 = pindx_tb_71;
        _zz_149 = pfunc_tb_71_real;
        _zz_150 = pfunc_tb_71_imag;
      end
      7'b1001000 : begin
        _zz_148 = pindx_tb_72;
        _zz_149 = pfunc_tb_72_real;
        _zz_150 = pfunc_tb_72_imag;
      end
      7'b1001001 : begin
        _zz_148 = pindx_tb_73;
        _zz_149 = pfunc_tb_73_real;
        _zz_150 = pfunc_tb_73_imag;
      end
      7'b1001010 : begin
        _zz_148 = pindx_tb_74;
        _zz_149 = pfunc_tb_74_real;
        _zz_150 = pfunc_tb_74_imag;
      end
      7'b1001011 : begin
        _zz_148 = pindx_tb_75;
        _zz_149 = pfunc_tb_75_real;
        _zz_150 = pfunc_tb_75_imag;
      end
      7'b1001100 : begin
        _zz_148 = pindx_tb_76;
        _zz_149 = pfunc_tb_76_real;
        _zz_150 = pfunc_tb_76_imag;
      end
      7'b1001101 : begin
        _zz_148 = pindx_tb_77;
        _zz_149 = pfunc_tb_77_real;
        _zz_150 = pfunc_tb_77_imag;
      end
      7'b1001110 : begin
        _zz_148 = pindx_tb_78;
        _zz_149 = pfunc_tb_78_real;
        _zz_150 = pfunc_tb_78_imag;
      end
      7'b1001111 : begin
        _zz_148 = pindx_tb_79;
        _zz_149 = pfunc_tb_79_real;
        _zz_150 = pfunc_tb_79_imag;
      end
      7'b1010000 : begin
        _zz_148 = pindx_tb_80;
        _zz_149 = pfunc_tb_80_real;
        _zz_150 = pfunc_tb_80_imag;
      end
      7'b1010001 : begin
        _zz_148 = pindx_tb_81;
        _zz_149 = pfunc_tb_81_real;
        _zz_150 = pfunc_tb_81_imag;
      end
      7'b1010010 : begin
        _zz_148 = pindx_tb_82;
        _zz_149 = pfunc_tb_82_real;
        _zz_150 = pfunc_tb_82_imag;
      end
      7'b1010011 : begin
        _zz_148 = pindx_tb_83;
        _zz_149 = pfunc_tb_83_real;
        _zz_150 = pfunc_tb_83_imag;
      end
      7'b1010100 : begin
        _zz_148 = pindx_tb_84;
        _zz_149 = pfunc_tb_84_real;
        _zz_150 = pfunc_tb_84_imag;
      end
      7'b1010101 : begin
        _zz_148 = pindx_tb_85;
        _zz_149 = pfunc_tb_85_real;
        _zz_150 = pfunc_tb_85_imag;
      end
      7'b1010110 : begin
        _zz_148 = pindx_tb_86;
        _zz_149 = pfunc_tb_86_real;
        _zz_150 = pfunc_tb_86_imag;
      end
      7'b1010111 : begin
        _zz_148 = pindx_tb_87;
        _zz_149 = pfunc_tb_87_real;
        _zz_150 = pfunc_tb_87_imag;
      end
      7'b1011000 : begin
        _zz_148 = pindx_tb_88;
        _zz_149 = pfunc_tb_88_real;
        _zz_150 = pfunc_tb_88_imag;
      end
      7'b1011001 : begin
        _zz_148 = pindx_tb_89;
        _zz_149 = pfunc_tb_89_real;
        _zz_150 = pfunc_tb_89_imag;
      end
      7'b1011010 : begin
        _zz_148 = pindx_tb_90;
        _zz_149 = pfunc_tb_90_real;
        _zz_150 = pfunc_tb_90_imag;
      end
      7'b1011011 : begin
        _zz_148 = pindx_tb_91;
        _zz_149 = pfunc_tb_91_real;
        _zz_150 = pfunc_tb_91_imag;
      end
      7'b1011100 : begin
        _zz_148 = pindx_tb_92;
        _zz_149 = pfunc_tb_92_real;
        _zz_150 = pfunc_tb_92_imag;
      end
      7'b1011101 : begin
        _zz_148 = pindx_tb_93;
        _zz_149 = pfunc_tb_93_real;
        _zz_150 = pfunc_tb_93_imag;
      end
      7'b1011110 : begin
        _zz_148 = pindx_tb_94;
        _zz_149 = pfunc_tb_94_real;
        _zz_150 = pfunc_tb_94_imag;
      end
      7'b1011111 : begin
        _zz_148 = pindx_tb_95;
        _zz_149 = pfunc_tb_95_real;
        _zz_150 = pfunc_tb_95_imag;
      end
      7'b1100000 : begin
        _zz_148 = pindx_tb_96;
        _zz_149 = pfunc_tb_96_real;
        _zz_150 = pfunc_tb_96_imag;
      end
      7'b1100001 : begin
        _zz_148 = pindx_tb_97;
        _zz_149 = pfunc_tb_97_real;
        _zz_150 = pfunc_tb_97_imag;
      end
      7'b1100010 : begin
        _zz_148 = pindx_tb_98;
        _zz_149 = pfunc_tb_98_real;
        _zz_150 = pfunc_tb_98_imag;
      end
      7'b1100011 : begin
        _zz_148 = pindx_tb_99;
        _zz_149 = pfunc_tb_99_real;
        _zz_150 = pfunc_tb_99_imag;
      end
      7'b1100100 : begin
        _zz_148 = pindx_tb_100;
        _zz_149 = pfunc_tb_100_real;
        _zz_150 = pfunc_tb_100_imag;
      end
      7'b1100101 : begin
        _zz_148 = pindx_tb_101;
        _zz_149 = pfunc_tb_101_real;
        _zz_150 = pfunc_tb_101_imag;
      end
      7'b1100110 : begin
        _zz_148 = pindx_tb_102;
        _zz_149 = pfunc_tb_102_real;
        _zz_150 = pfunc_tb_102_imag;
      end
      7'b1100111 : begin
        _zz_148 = pindx_tb_103;
        _zz_149 = pfunc_tb_103_real;
        _zz_150 = pfunc_tb_103_imag;
      end
      7'b1101000 : begin
        _zz_148 = pindx_tb_104;
        _zz_149 = pfunc_tb_104_real;
        _zz_150 = pfunc_tb_104_imag;
      end
      7'b1101001 : begin
        _zz_148 = pindx_tb_105;
        _zz_149 = pfunc_tb_105_real;
        _zz_150 = pfunc_tb_105_imag;
      end
      7'b1101010 : begin
        _zz_148 = pindx_tb_106;
        _zz_149 = pfunc_tb_106_real;
        _zz_150 = pfunc_tb_106_imag;
      end
      7'b1101011 : begin
        _zz_148 = pindx_tb_107;
        _zz_149 = pfunc_tb_107_real;
        _zz_150 = pfunc_tb_107_imag;
      end
      7'b1101100 : begin
        _zz_148 = pindx_tb_108;
        _zz_149 = pfunc_tb_108_real;
        _zz_150 = pfunc_tb_108_imag;
      end
      7'b1101101 : begin
        _zz_148 = pindx_tb_109;
        _zz_149 = pfunc_tb_109_real;
        _zz_150 = pfunc_tb_109_imag;
      end
      7'b1101110 : begin
        _zz_148 = pindx_tb_110;
        _zz_149 = pfunc_tb_110_real;
        _zz_150 = pfunc_tb_110_imag;
      end
      7'b1101111 : begin
        _zz_148 = pindx_tb_111;
        _zz_149 = pfunc_tb_111_real;
        _zz_150 = pfunc_tb_111_imag;
      end
      7'b1110000 : begin
        _zz_148 = pindx_tb_112;
        _zz_149 = pfunc_tb_112_real;
        _zz_150 = pfunc_tb_112_imag;
      end
      7'b1110001 : begin
        _zz_148 = pindx_tb_113;
        _zz_149 = pfunc_tb_113_real;
        _zz_150 = pfunc_tb_113_imag;
      end
      7'b1110010 : begin
        _zz_148 = pindx_tb_114;
        _zz_149 = pfunc_tb_114_real;
        _zz_150 = pfunc_tb_114_imag;
      end
      7'b1110011 : begin
        _zz_148 = pindx_tb_115;
        _zz_149 = pfunc_tb_115_real;
        _zz_150 = pfunc_tb_115_imag;
      end
      7'b1110100 : begin
        _zz_148 = pindx_tb_116;
        _zz_149 = pfunc_tb_116_real;
        _zz_150 = pfunc_tb_116_imag;
      end
      7'b1110101 : begin
        _zz_148 = pindx_tb_117;
        _zz_149 = pfunc_tb_117_real;
        _zz_150 = pfunc_tb_117_imag;
      end
      7'b1110110 : begin
        _zz_148 = pindx_tb_118;
        _zz_149 = pfunc_tb_118_real;
        _zz_150 = pfunc_tb_118_imag;
      end
      7'b1110111 : begin
        _zz_148 = pindx_tb_119;
        _zz_149 = pfunc_tb_119_real;
        _zz_150 = pfunc_tb_119_imag;
      end
      7'b1111000 : begin
        _zz_148 = pindx_tb_120;
        _zz_149 = pfunc_tb_120_real;
        _zz_150 = pfunc_tb_120_imag;
      end
      7'b1111001 : begin
        _zz_148 = pindx_tb_121;
        _zz_149 = pfunc_tb_121_real;
        _zz_150 = pfunc_tb_121_imag;
      end
      7'b1111010 : begin
        _zz_148 = pindx_tb_122;
        _zz_149 = pfunc_tb_122_real;
        _zz_150 = pfunc_tb_122_imag;
      end
      7'b1111011 : begin
        _zz_148 = pindx_tb_123;
        _zz_149 = pfunc_tb_123_real;
        _zz_150 = pfunc_tb_123_imag;
      end
      7'b1111100 : begin
        _zz_148 = pindx_tb_124;
        _zz_149 = pfunc_tb_124_real;
        _zz_150 = pfunc_tb_124_imag;
      end
      7'b1111101 : begin
        _zz_148 = pindx_tb_125;
        _zz_149 = pfunc_tb_125_real;
        _zz_150 = pfunc_tb_125_imag;
      end
      7'b1111110 : begin
        _zz_148 = pindx_tb_126;
        _zz_149 = pfunc_tb_126_real;
        _zz_150 = pfunc_tb_126_imag;
      end
      default : begin
        _zz_148 = pindx_tb_127;
        _zz_149 = pfunc_tb_127_real;
        _zz_150 = pfunc_tb_127_imag;
      end
    endcase
  end

  assign fx_period = 32'h00010000;
  assign _zz_1 = _zz_154[31 : 0];
  always @ (*) begin
    if(fixTo_dout[31])begin
      indx = ($signed(_zz_1) + $signed(fx_period));
    end else begin
      indx = _zz_1;
    end
  end

  assign pindx_tb_0 = 32'h00000200;
  assign pindx_tb_1 = 32'h00000400;
  assign pindx_tb_2 = 32'h00000600;
  assign pindx_tb_3 = 32'h00000800;
  assign pindx_tb_4 = 32'h00000a00;
  assign pindx_tb_5 = 32'h00000c00;
  assign pindx_tb_6 = 32'h00000e00;
  assign pindx_tb_7 = 32'h00001000;
  assign pindx_tb_8 = 32'h00001200;
  assign pindx_tb_9 = 32'h00001400;
  assign pindx_tb_10 = 32'h00001600;
  assign pindx_tb_11 = 32'h00001800;
  assign pindx_tb_12 = 32'h00001a00;
  assign pindx_tb_13 = 32'h00001c00;
  assign pindx_tb_14 = 32'h00001e00;
  assign pindx_tb_15 = 32'h00002000;
  assign pindx_tb_16 = 32'h00002200;
  assign pindx_tb_17 = 32'h00002400;
  assign pindx_tb_18 = 32'h00002600;
  assign pindx_tb_19 = 32'h00002800;
  assign pindx_tb_20 = 32'h00002a00;
  assign pindx_tb_21 = 32'h00002c00;
  assign pindx_tb_22 = 32'h00002e00;
  assign pindx_tb_23 = 32'h00003000;
  assign pindx_tb_24 = 32'h00003200;
  assign pindx_tb_25 = 32'h00003400;
  assign pindx_tb_26 = 32'h00003600;
  assign pindx_tb_27 = 32'h00003800;
  assign pindx_tb_28 = 32'h00003a00;
  assign pindx_tb_29 = 32'h00003c00;
  assign pindx_tb_30 = 32'h00003e00;
  assign pindx_tb_31 = 32'h00004000;
  assign pindx_tb_32 = 32'h00004200;
  assign pindx_tb_33 = 32'h00004400;
  assign pindx_tb_34 = 32'h00004600;
  assign pindx_tb_35 = 32'h00004800;
  assign pindx_tb_36 = 32'h00004a00;
  assign pindx_tb_37 = 32'h00004c00;
  assign pindx_tb_38 = 32'h00004e00;
  assign pindx_tb_39 = 32'h00005000;
  assign pindx_tb_40 = 32'h00005200;
  assign pindx_tb_41 = 32'h00005400;
  assign pindx_tb_42 = 32'h00005600;
  assign pindx_tb_43 = 32'h00005800;
  assign pindx_tb_44 = 32'h00005a00;
  assign pindx_tb_45 = 32'h00005c00;
  assign pindx_tb_46 = 32'h00005e00;
  assign pindx_tb_47 = 32'h00006000;
  assign pindx_tb_48 = 32'h00006200;
  assign pindx_tb_49 = 32'h00006400;
  assign pindx_tb_50 = 32'h00006600;
  assign pindx_tb_51 = 32'h00006800;
  assign pindx_tb_52 = 32'h00006a00;
  assign pindx_tb_53 = 32'h00006c00;
  assign pindx_tb_54 = 32'h00006e00;
  assign pindx_tb_55 = 32'h00007000;
  assign pindx_tb_56 = 32'h00007200;
  assign pindx_tb_57 = 32'h00007400;
  assign pindx_tb_58 = 32'h00007600;
  assign pindx_tb_59 = 32'h00007800;
  assign pindx_tb_60 = 32'h00007a00;
  assign pindx_tb_61 = 32'h00007c00;
  assign pindx_tb_62 = 32'h00007e00;
  assign pindx_tb_63 = 32'h00008000;
  assign pindx_tb_64 = 32'h00008200;
  assign pindx_tb_65 = 32'h00008400;
  assign pindx_tb_66 = 32'h00008600;
  assign pindx_tb_67 = 32'h00008800;
  assign pindx_tb_68 = 32'h00008a00;
  assign pindx_tb_69 = 32'h00008c00;
  assign pindx_tb_70 = 32'h00008e00;
  assign pindx_tb_71 = 32'h00009000;
  assign pindx_tb_72 = 32'h00009200;
  assign pindx_tb_73 = 32'h00009400;
  assign pindx_tb_74 = 32'h00009600;
  assign pindx_tb_75 = 32'h00009800;
  assign pindx_tb_76 = 32'h00009a00;
  assign pindx_tb_77 = 32'h00009c00;
  assign pindx_tb_78 = 32'h00009e00;
  assign pindx_tb_79 = 32'h0000a000;
  assign pindx_tb_80 = 32'h0000a200;
  assign pindx_tb_81 = 32'h0000a400;
  assign pindx_tb_82 = 32'h0000a600;
  assign pindx_tb_83 = 32'h0000a800;
  assign pindx_tb_84 = 32'h0000aa00;
  assign pindx_tb_85 = 32'h0000ac00;
  assign pindx_tb_86 = 32'h0000ae00;
  assign pindx_tb_87 = 32'h0000b000;
  assign pindx_tb_88 = 32'h0000b200;
  assign pindx_tb_89 = 32'h0000b400;
  assign pindx_tb_90 = 32'h0000b600;
  assign pindx_tb_91 = 32'h0000b800;
  assign pindx_tb_92 = 32'h0000ba00;
  assign pindx_tb_93 = 32'h0000bc00;
  assign pindx_tb_94 = 32'h0000be00;
  assign pindx_tb_95 = 32'h0000c000;
  assign pindx_tb_96 = 32'h0000c200;
  assign pindx_tb_97 = 32'h0000c400;
  assign pindx_tb_98 = 32'h0000c600;
  assign pindx_tb_99 = 32'h0000c800;
  assign pindx_tb_100 = 32'h0000ca00;
  assign pindx_tb_101 = 32'h0000cc00;
  assign pindx_tb_102 = 32'h0000ce00;
  assign pindx_tb_103 = 32'h0000d000;
  assign pindx_tb_104 = 32'h0000d200;
  assign pindx_tb_105 = 32'h0000d400;
  assign pindx_tb_106 = 32'h0000d600;
  assign pindx_tb_107 = 32'h0000d800;
  assign pindx_tb_108 = 32'h0000da00;
  assign pindx_tb_109 = 32'h0000dc00;
  assign pindx_tb_110 = 32'h0000de00;
  assign pindx_tb_111 = 32'h0000e000;
  assign pindx_tb_112 = 32'h0000e200;
  assign pindx_tb_113 = 32'h0000e400;
  assign pindx_tb_114 = 32'h0000e600;
  assign pindx_tb_115 = 32'h0000e800;
  assign pindx_tb_116 = 32'h0000ea00;
  assign pindx_tb_117 = 32'h0000ec00;
  assign pindx_tb_118 = 32'h0000ee00;
  assign pindx_tb_119 = 32'h0000f000;
  assign pindx_tb_120 = 32'h0000f200;
  assign pindx_tb_121 = 32'h0000f400;
  assign pindx_tb_122 = 32'h0000f600;
  assign pindx_tb_123 = 32'h0000f800;
  assign pindx_tb_124 = 32'h0000fa00;
  assign pindx_tb_125 = 32'h0000fc00;
  assign pindx_tb_126 = 32'h0000fe00;
  assign pindx_tb_127 = 32'h00010000;
  assign pfunc_tb_0_real = 32'h0000ffb1;
  assign pfunc_tb_0_imag = 32'h00000c8f;
  assign pfunc_tb_1_real = 32'h0000fec4;
  assign pfunc_tb_1_imag = 32'h00001917;
  assign pfunc_tb_2_real = 32'h0000fd3a;
  assign pfunc_tb_2_imag = 32'h00002590;
  assign pfunc_tb_3_real = 32'h0000fb14;
  assign pfunc_tb_3_imag = 32'h000031f1;
  assign pfunc_tb_4_real = 32'h0000f853;
  assign pfunc_tb_4_imag = 32'h00003e33;
  assign pfunc_tb_5_real = 32'h0000f4fa;
  assign pfunc_tb_5_imag = 32'h00004a50;
  assign pfunc_tb_6_real = 32'h0000f109;
  assign pfunc_tb_6_imag = 32'h0000563e;
  assign pfunc_tb_7_real = 32'h0000ec83;
  assign pfunc_tb_7_imag = 32'h000061f7;
  assign pfunc_tb_8_real = 32'h0000e76b;
  assign pfunc_tb_8_imag = 32'h00006d74;
  assign pfunc_tb_9_real = 32'h0000e1c5;
  assign pfunc_tb_9_imag = 32'h000078ad;
  assign pfunc_tb_10_real = 32'h0000db94;
  assign pfunc_tb_10_imag = 32'h0000839c;
  assign pfunc_tb_11_real = 32'h0000d4db;
  assign pfunc_tb_11_imag = 32'h00008e39;
  assign pfunc_tb_12_real = 32'h0000cd9f;
  assign pfunc_tb_12_imag = 32'h0000987f;
  assign pfunc_tb_13_real = 32'h0000c5e4;
  assign pfunc_tb_13_imag = 32'h0000a267;
  assign pfunc_tb_14_real = 32'h0000bdae;
  assign pfunc_tb_14_imag = 32'h0000abeb;
  assign pfunc_tb_15_real = 32'h0000b504;
  assign pfunc_tb_15_imag = 32'h0000b504;
  assign pfunc_tb_16_real = 32'h0000abeb;
  assign pfunc_tb_16_imag = 32'h0000bdae;
  assign pfunc_tb_17_real = 32'h0000a267;
  assign pfunc_tb_17_imag = 32'h0000c5e4;
  assign pfunc_tb_18_real = 32'h0000987f;
  assign pfunc_tb_18_imag = 32'h0000cd9f;
  assign pfunc_tb_19_real = 32'h00008e39;
  assign pfunc_tb_19_imag = 32'h0000d4db;
  assign pfunc_tb_20_real = 32'h0000839c;
  assign pfunc_tb_20_imag = 32'h0000db94;
  assign pfunc_tb_21_real = 32'h000078ad;
  assign pfunc_tb_21_imag = 32'h0000e1c5;
  assign pfunc_tb_22_real = 32'h00006d74;
  assign pfunc_tb_22_imag = 32'h0000e76b;
  assign pfunc_tb_23_real = 32'h000061f7;
  assign pfunc_tb_23_imag = 32'h0000ec83;
  assign pfunc_tb_24_real = 32'h0000563e;
  assign pfunc_tb_24_imag = 32'h0000f109;
  assign pfunc_tb_25_real = 32'h00004a50;
  assign pfunc_tb_25_imag = 32'h0000f4fa;
  assign pfunc_tb_26_real = 32'h00003e33;
  assign pfunc_tb_26_imag = 32'h0000f853;
  assign pfunc_tb_27_real = 32'h000031f1;
  assign pfunc_tb_27_imag = 32'h0000fb14;
  assign pfunc_tb_28_real = 32'h00002590;
  assign pfunc_tb_28_imag = 32'h0000fd3a;
  assign pfunc_tb_29_real = 32'h00001917;
  assign pfunc_tb_29_imag = 32'h0000fec4;
  assign pfunc_tb_30_real = 32'h00000c8f;
  assign pfunc_tb_30_imag = 32'h0000ffb1;
  assign pfunc_tb_31_real = 32'h0;
  assign pfunc_tb_31_imag = 32'h00010000;
  assign pfunc_tb_32_real = 32'hfffff371;
  assign pfunc_tb_32_imag = 32'h0000ffb1;
  assign pfunc_tb_33_real = 32'hffffe6e9;
  assign pfunc_tb_33_imag = 32'h0000fec4;
  assign pfunc_tb_34_real = 32'hffffda70;
  assign pfunc_tb_34_imag = 32'h0000fd3a;
  assign pfunc_tb_35_real = 32'hffffce0f;
  assign pfunc_tb_35_imag = 32'h0000fb14;
  assign pfunc_tb_36_real = 32'hffffc1cd;
  assign pfunc_tb_36_imag = 32'h0000f853;
  assign pfunc_tb_37_real = 32'hffffb5b0;
  assign pfunc_tb_37_imag = 32'h0000f4fa;
  assign pfunc_tb_38_real = 32'hffffa9c2;
  assign pfunc_tb_38_imag = 32'h0000f109;
  assign pfunc_tb_39_real = 32'hffff9e09;
  assign pfunc_tb_39_imag = 32'h0000ec83;
  assign pfunc_tb_40_real = 32'hffff928c;
  assign pfunc_tb_40_imag = 32'h0000e76b;
  assign pfunc_tb_41_real = 32'hffff8753;
  assign pfunc_tb_41_imag = 32'h0000e1c5;
  assign pfunc_tb_42_real = 32'hffff7c64;
  assign pfunc_tb_42_imag = 32'h0000db94;
  assign pfunc_tb_43_real = 32'hffff71c7;
  assign pfunc_tb_43_imag = 32'h0000d4db;
  assign pfunc_tb_44_real = 32'hffff6781;
  assign pfunc_tb_44_imag = 32'h0000cd9f;
  assign pfunc_tb_45_real = 32'hffff5d99;
  assign pfunc_tb_45_imag = 32'h0000c5e4;
  assign pfunc_tb_46_real = 32'hffff5415;
  assign pfunc_tb_46_imag = 32'h0000bdae;
  assign pfunc_tb_47_real = 32'hffff4afc;
  assign pfunc_tb_47_imag = 32'h0000b504;
  assign pfunc_tb_48_real = 32'hffff4252;
  assign pfunc_tb_48_imag = 32'h0000abeb;
  assign pfunc_tb_49_real = 32'hffff3a1c;
  assign pfunc_tb_49_imag = 32'h0000a267;
  assign pfunc_tb_50_real = 32'hffff3261;
  assign pfunc_tb_50_imag = 32'h0000987f;
  assign pfunc_tb_51_real = 32'hffff2b25;
  assign pfunc_tb_51_imag = 32'h00008e39;
  assign pfunc_tb_52_real = 32'hffff246c;
  assign pfunc_tb_52_imag = 32'h0000839c;
  assign pfunc_tb_53_real = 32'hffff1e3b;
  assign pfunc_tb_53_imag = 32'h000078ad;
  assign pfunc_tb_54_real = 32'hffff1895;
  assign pfunc_tb_54_imag = 32'h00006d74;
  assign pfunc_tb_55_real = 32'hffff137d;
  assign pfunc_tb_55_imag = 32'h000061f7;
  assign pfunc_tb_56_real = 32'hffff0ef7;
  assign pfunc_tb_56_imag = 32'h0000563e;
  assign pfunc_tb_57_real = 32'hffff0b06;
  assign pfunc_tb_57_imag = 32'h00004a50;
  assign pfunc_tb_58_real = 32'hffff07ad;
  assign pfunc_tb_58_imag = 32'h00003e33;
  assign pfunc_tb_59_real = 32'hffff04ec;
  assign pfunc_tb_59_imag = 32'h000031f1;
  assign pfunc_tb_60_real = 32'hffff02c6;
  assign pfunc_tb_60_imag = 32'h00002590;
  assign pfunc_tb_61_real = 32'hffff013c;
  assign pfunc_tb_61_imag = 32'h00001917;
  assign pfunc_tb_62_real = 32'hffff004f;
  assign pfunc_tb_62_imag = 32'h00000c8f;
  assign pfunc_tb_63_real = 32'hffff0000;
  assign pfunc_tb_63_imag = 32'h0;
  assign pfunc_tb_64_real = 32'hffff004f;
  assign pfunc_tb_64_imag = 32'hfffff371;
  assign pfunc_tb_65_real = 32'hffff013c;
  assign pfunc_tb_65_imag = 32'hffffe6e9;
  assign pfunc_tb_66_real = 32'hffff02c6;
  assign pfunc_tb_66_imag = 32'hffffda70;
  assign pfunc_tb_67_real = 32'hffff04ec;
  assign pfunc_tb_67_imag = 32'hffffce0f;
  assign pfunc_tb_68_real = 32'hffff07ad;
  assign pfunc_tb_68_imag = 32'hffffc1cd;
  assign pfunc_tb_69_real = 32'hffff0b06;
  assign pfunc_tb_69_imag = 32'hffffb5b0;
  assign pfunc_tb_70_real = 32'hffff0ef7;
  assign pfunc_tb_70_imag = 32'hffffa9c2;
  assign pfunc_tb_71_real = 32'hffff137d;
  assign pfunc_tb_71_imag = 32'hffff9e09;
  assign pfunc_tb_72_real = 32'hffff1895;
  assign pfunc_tb_72_imag = 32'hffff928c;
  assign pfunc_tb_73_real = 32'hffff1e3b;
  assign pfunc_tb_73_imag = 32'hffff8753;
  assign pfunc_tb_74_real = 32'hffff246c;
  assign pfunc_tb_74_imag = 32'hffff7c64;
  assign pfunc_tb_75_real = 32'hffff2b25;
  assign pfunc_tb_75_imag = 32'hffff71c7;
  assign pfunc_tb_76_real = 32'hffff3261;
  assign pfunc_tb_76_imag = 32'hffff6781;
  assign pfunc_tb_77_real = 32'hffff3a1c;
  assign pfunc_tb_77_imag = 32'hffff5d99;
  assign pfunc_tb_78_real = 32'hffff4252;
  assign pfunc_tb_78_imag = 32'hffff5415;
  assign pfunc_tb_79_real = 32'hffff4afc;
  assign pfunc_tb_79_imag = 32'hffff4afc;
  assign pfunc_tb_80_real = 32'hffff5415;
  assign pfunc_tb_80_imag = 32'hffff4252;
  assign pfunc_tb_81_real = 32'hffff5d99;
  assign pfunc_tb_81_imag = 32'hffff3a1c;
  assign pfunc_tb_82_real = 32'hffff6781;
  assign pfunc_tb_82_imag = 32'hffff3261;
  assign pfunc_tb_83_real = 32'hffff71c7;
  assign pfunc_tb_83_imag = 32'hffff2b25;
  assign pfunc_tb_84_real = 32'hffff7c64;
  assign pfunc_tb_84_imag = 32'hffff246c;
  assign pfunc_tb_85_real = 32'hffff8753;
  assign pfunc_tb_85_imag = 32'hffff1e3b;
  assign pfunc_tb_86_real = 32'hffff928c;
  assign pfunc_tb_86_imag = 32'hffff1895;
  assign pfunc_tb_87_real = 32'hffff9e09;
  assign pfunc_tb_87_imag = 32'hffff137d;
  assign pfunc_tb_88_real = 32'hffffa9c2;
  assign pfunc_tb_88_imag = 32'hffff0ef7;
  assign pfunc_tb_89_real = 32'hffffb5b0;
  assign pfunc_tb_89_imag = 32'hffff0b06;
  assign pfunc_tb_90_real = 32'hffffc1cd;
  assign pfunc_tb_90_imag = 32'hffff07ad;
  assign pfunc_tb_91_real = 32'hffffce0f;
  assign pfunc_tb_91_imag = 32'hffff04ec;
  assign pfunc_tb_92_real = 32'hffffda70;
  assign pfunc_tb_92_imag = 32'hffff02c6;
  assign pfunc_tb_93_real = 32'hffffe6e9;
  assign pfunc_tb_93_imag = 32'hffff013c;
  assign pfunc_tb_94_real = 32'hfffff371;
  assign pfunc_tb_94_imag = 32'hffff004f;
  assign pfunc_tb_95_real = 32'h0;
  assign pfunc_tb_95_imag = 32'hffff0000;
  assign pfunc_tb_96_real = 32'h00000c8f;
  assign pfunc_tb_96_imag = 32'hffff004f;
  assign pfunc_tb_97_real = 32'h00001917;
  assign pfunc_tb_97_imag = 32'hffff013c;
  assign pfunc_tb_98_real = 32'h00002590;
  assign pfunc_tb_98_imag = 32'hffff02c6;
  assign pfunc_tb_99_real = 32'h000031f1;
  assign pfunc_tb_99_imag = 32'hffff04ec;
  assign pfunc_tb_100_real = 32'h00003e33;
  assign pfunc_tb_100_imag = 32'hffff07ad;
  assign pfunc_tb_101_real = 32'h00004a50;
  assign pfunc_tb_101_imag = 32'hffff0b06;
  assign pfunc_tb_102_real = 32'h0000563e;
  assign pfunc_tb_102_imag = 32'hffff0ef7;
  assign pfunc_tb_103_real = 32'h000061f7;
  assign pfunc_tb_103_imag = 32'hffff137d;
  assign pfunc_tb_104_real = 32'h00006d74;
  assign pfunc_tb_104_imag = 32'hffff1895;
  assign pfunc_tb_105_real = 32'h000078ad;
  assign pfunc_tb_105_imag = 32'hffff1e3b;
  assign pfunc_tb_106_real = 32'h0000839c;
  assign pfunc_tb_106_imag = 32'hffff246c;
  assign pfunc_tb_107_real = 32'h00008e39;
  assign pfunc_tb_107_imag = 32'hffff2b25;
  assign pfunc_tb_108_real = 32'h0000987f;
  assign pfunc_tb_108_imag = 32'hffff3261;
  assign pfunc_tb_109_real = 32'h0000a267;
  assign pfunc_tb_109_imag = 32'hffff3a1c;
  assign pfunc_tb_110_real = 32'h0000abeb;
  assign pfunc_tb_110_imag = 32'hffff4252;
  assign pfunc_tb_111_real = 32'h0000b504;
  assign pfunc_tb_111_imag = 32'hffff4afc;
  assign pfunc_tb_112_real = 32'h0000bdae;
  assign pfunc_tb_112_imag = 32'hffff5415;
  assign pfunc_tb_113_real = 32'h0000c5e4;
  assign pfunc_tb_113_imag = 32'hffff5d99;
  assign pfunc_tb_114_real = 32'h0000cd9f;
  assign pfunc_tb_114_imag = 32'hffff6781;
  assign pfunc_tb_115_real = 32'h0000d4db;
  assign pfunc_tb_115_imag = 32'hffff71c7;
  assign pfunc_tb_116_real = 32'h0000db94;
  assign pfunc_tb_116_imag = 32'hffff7c64;
  assign pfunc_tb_117_real = 32'h0000e1c5;
  assign pfunc_tb_117_imag = 32'hffff8753;
  assign pfunc_tb_118_real = 32'h0000e76b;
  assign pfunc_tb_118_imag = 32'hffff928c;
  assign pfunc_tb_119_real = 32'h0000ec83;
  assign pfunc_tb_119_imag = 32'hffff9e09;
  assign pfunc_tb_120_real = 32'h0000f109;
  assign pfunc_tb_120_imag = 32'hffffa9c2;
  assign pfunc_tb_121_real = 32'h0000f4fa;
  assign pfunc_tb_121_imag = 32'hffffb5b0;
  assign pfunc_tb_122_real = 32'h0000f853;
  assign pfunc_tb_122_imag = 32'hffffc1cd;
  assign pfunc_tb_123_real = 32'h0000fb14;
  assign pfunc_tb_123_imag = 32'hffffce0f;
  assign pfunc_tb_124_real = 32'h0000fd3a;
  assign pfunc_tb_124_imag = 32'hffffda70;
  assign pfunc_tb_125_real = 32'h0000fec4;
  assign pfunc_tb_125_imag = 32'hffffe6e9;
  assign pfunc_tb_126_real = 32'h0000ffb1;
  assign pfunc_tb_126_imag = 32'hfffff371;
  assign pfunc_tb_127_real = 32'h00010000;
  assign pfunc_tb_127_imag = 32'h0;
  assign idx_comp_0 = ($signed(indx) < $signed(pindx_tb_0));
  assign idx_comp_1 = ($signed(indx) < $signed(pindx_tb_1));
  assign idx_comp_2 = ($signed(indx) < $signed(pindx_tb_2));
  assign idx_comp_3 = ($signed(indx) < $signed(pindx_tb_3));
  assign idx_comp_4 = ($signed(indx) < $signed(pindx_tb_4));
  assign idx_comp_5 = ($signed(indx) < $signed(pindx_tb_5));
  assign idx_comp_6 = ($signed(indx) < $signed(pindx_tb_6));
  assign idx_comp_7 = ($signed(indx) < $signed(pindx_tb_7));
  assign idx_comp_8 = ($signed(indx) < $signed(pindx_tb_8));
  assign idx_comp_9 = ($signed(indx) < $signed(pindx_tb_9));
  assign idx_comp_10 = ($signed(indx) < $signed(pindx_tb_10));
  assign idx_comp_11 = ($signed(indx) < $signed(pindx_tb_11));
  assign idx_comp_12 = ($signed(indx) < $signed(pindx_tb_12));
  assign idx_comp_13 = ($signed(indx) < $signed(pindx_tb_13));
  assign idx_comp_14 = ($signed(indx) < $signed(pindx_tb_14));
  assign idx_comp_15 = ($signed(indx) < $signed(pindx_tb_15));
  assign idx_comp_16 = ($signed(indx) < $signed(pindx_tb_16));
  assign idx_comp_17 = ($signed(indx) < $signed(pindx_tb_17));
  assign idx_comp_18 = ($signed(indx) < $signed(pindx_tb_18));
  assign idx_comp_19 = ($signed(indx) < $signed(pindx_tb_19));
  assign idx_comp_20 = ($signed(indx) < $signed(pindx_tb_20));
  assign idx_comp_21 = ($signed(indx) < $signed(pindx_tb_21));
  assign idx_comp_22 = ($signed(indx) < $signed(pindx_tb_22));
  assign idx_comp_23 = ($signed(indx) < $signed(pindx_tb_23));
  assign idx_comp_24 = ($signed(indx) < $signed(pindx_tb_24));
  assign idx_comp_25 = ($signed(indx) < $signed(pindx_tb_25));
  assign idx_comp_26 = ($signed(indx) < $signed(pindx_tb_26));
  assign idx_comp_27 = ($signed(indx) < $signed(pindx_tb_27));
  assign idx_comp_28 = ($signed(indx) < $signed(pindx_tb_28));
  assign idx_comp_29 = ($signed(indx) < $signed(pindx_tb_29));
  assign idx_comp_30 = ($signed(indx) < $signed(pindx_tb_30));
  assign idx_comp_31 = ($signed(indx) < $signed(pindx_tb_31));
  assign idx_comp_32 = ($signed(indx) < $signed(pindx_tb_32));
  assign idx_comp_33 = ($signed(indx) < $signed(pindx_tb_33));
  assign idx_comp_34 = ($signed(indx) < $signed(pindx_tb_34));
  assign idx_comp_35 = ($signed(indx) < $signed(pindx_tb_35));
  assign idx_comp_36 = ($signed(indx) < $signed(pindx_tb_36));
  assign idx_comp_37 = ($signed(indx) < $signed(pindx_tb_37));
  assign idx_comp_38 = ($signed(indx) < $signed(pindx_tb_38));
  assign idx_comp_39 = ($signed(indx) < $signed(pindx_tb_39));
  assign idx_comp_40 = ($signed(indx) < $signed(pindx_tb_40));
  assign idx_comp_41 = ($signed(indx) < $signed(pindx_tb_41));
  assign idx_comp_42 = ($signed(indx) < $signed(pindx_tb_42));
  assign idx_comp_43 = ($signed(indx) < $signed(pindx_tb_43));
  assign idx_comp_44 = ($signed(indx) < $signed(pindx_tb_44));
  assign idx_comp_45 = ($signed(indx) < $signed(pindx_tb_45));
  assign idx_comp_46 = ($signed(indx) < $signed(pindx_tb_46));
  assign idx_comp_47 = ($signed(indx) < $signed(pindx_tb_47));
  assign idx_comp_48 = ($signed(indx) < $signed(pindx_tb_48));
  assign idx_comp_49 = ($signed(indx) < $signed(pindx_tb_49));
  assign idx_comp_50 = ($signed(indx) < $signed(pindx_tb_50));
  assign idx_comp_51 = ($signed(indx) < $signed(pindx_tb_51));
  assign idx_comp_52 = ($signed(indx) < $signed(pindx_tb_52));
  assign idx_comp_53 = ($signed(indx) < $signed(pindx_tb_53));
  assign idx_comp_54 = ($signed(indx) < $signed(pindx_tb_54));
  assign idx_comp_55 = ($signed(indx) < $signed(pindx_tb_55));
  assign idx_comp_56 = ($signed(indx) < $signed(pindx_tb_56));
  assign idx_comp_57 = ($signed(indx) < $signed(pindx_tb_57));
  assign idx_comp_58 = ($signed(indx) < $signed(pindx_tb_58));
  assign idx_comp_59 = ($signed(indx) < $signed(pindx_tb_59));
  assign idx_comp_60 = ($signed(indx) < $signed(pindx_tb_60));
  assign idx_comp_61 = ($signed(indx) < $signed(pindx_tb_61));
  assign idx_comp_62 = ($signed(indx) < $signed(pindx_tb_62));
  assign idx_comp_63 = ($signed(indx) < $signed(pindx_tb_63));
  assign idx_comp_64 = ($signed(indx) < $signed(pindx_tb_64));
  assign idx_comp_65 = ($signed(indx) < $signed(pindx_tb_65));
  assign idx_comp_66 = ($signed(indx) < $signed(pindx_tb_66));
  assign idx_comp_67 = ($signed(indx) < $signed(pindx_tb_67));
  assign idx_comp_68 = ($signed(indx) < $signed(pindx_tb_68));
  assign idx_comp_69 = ($signed(indx) < $signed(pindx_tb_69));
  assign idx_comp_70 = ($signed(indx) < $signed(pindx_tb_70));
  assign idx_comp_71 = ($signed(indx) < $signed(pindx_tb_71));
  assign idx_comp_72 = ($signed(indx) < $signed(pindx_tb_72));
  assign idx_comp_73 = ($signed(indx) < $signed(pindx_tb_73));
  assign idx_comp_74 = ($signed(indx) < $signed(pindx_tb_74));
  assign idx_comp_75 = ($signed(indx) < $signed(pindx_tb_75));
  assign idx_comp_76 = ($signed(indx) < $signed(pindx_tb_76));
  assign idx_comp_77 = ($signed(indx) < $signed(pindx_tb_77));
  assign idx_comp_78 = ($signed(indx) < $signed(pindx_tb_78));
  assign idx_comp_79 = ($signed(indx) < $signed(pindx_tb_79));
  assign idx_comp_80 = ($signed(indx) < $signed(pindx_tb_80));
  assign idx_comp_81 = ($signed(indx) < $signed(pindx_tb_81));
  assign idx_comp_82 = ($signed(indx) < $signed(pindx_tb_82));
  assign idx_comp_83 = ($signed(indx) < $signed(pindx_tb_83));
  assign idx_comp_84 = ($signed(indx) < $signed(pindx_tb_84));
  assign idx_comp_85 = ($signed(indx) < $signed(pindx_tb_85));
  assign idx_comp_86 = ($signed(indx) < $signed(pindx_tb_86));
  assign idx_comp_87 = ($signed(indx) < $signed(pindx_tb_87));
  assign idx_comp_88 = ($signed(indx) < $signed(pindx_tb_88));
  assign idx_comp_89 = ($signed(indx) < $signed(pindx_tb_89));
  assign idx_comp_90 = ($signed(indx) < $signed(pindx_tb_90));
  assign idx_comp_91 = ($signed(indx) < $signed(pindx_tb_91));
  assign idx_comp_92 = ($signed(indx) < $signed(pindx_tb_92));
  assign idx_comp_93 = ($signed(indx) < $signed(pindx_tb_93));
  assign idx_comp_94 = ($signed(indx) < $signed(pindx_tb_94));
  assign idx_comp_95 = ($signed(indx) < $signed(pindx_tb_95));
  assign idx_comp_96 = ($signed(indx) < $signed(pindx_tb_96));
  assign idx_comp_97 = ($signed(indx) < $signed(pindx_tb_97));
  assign idx_comp_98 = ($signed(indx) < $signed(pindx_tb_98));
  assign idx_comp_99 = ($signed(indx) < $signed(pindx_tb_99));
  assign idx_comp_100 = ($signed(indx) < $signed(pindx_tb_100));
  assign idx_comp_101 = ($signed(indx) < $signed(pindx_tb_101));
  assign idx_comp_102 = ($signed(indx) < $signed(pindx_tb_102));
  assign idx_comp_103 = ($signed(indx) < $signed(pindx_tb_103));
  assign idx_comp_104 = ($signed(indx) < $signed(pindx_tb_104));
  assign idx_comp_105 = ($signed(indx) < $signed(pindx_tb_105));
  assign idx_comp_106 = ($signed(indx) < $signed(pindx_tb_106));
  assign idx_comp_107 = ($signed(indx) < $signed(pindx_tb_107));
  assign idx_comp_108 = ($signed(indx) < $signed(pindx_tb_108));
  assign idx_comp_109 = ($signed(indx) < $signed(pindx_tb_109));
  assign idx_comp_110 = ($signed(indx) < $signed(pindx_tb_110));
  assign idx_comp_111 = ($signed(indx) < $signed(pindx_tb_111));
  assign idx_comp_112 = ($signed(indx) < $signed(pindx_tb_112));
  assign idx_comp_113 = ($signed(indx) < $signed(pindx_tb_113));
  assign idx_comp_114 = ($signed(indx) < $signed(pindx_tb_114));
  assign idx_comp_115 = ($signed(indx) < $signed(pindx_tb_115));
  assign idx_comp_116 = ($signed(indx) < $signed(pindx_tb_116));
  assign idx_comp_117 = ($signed(indx) < $signed(pindx_tb_117));
  assign idx_comp_118 = ($signed(indx) < $signed(pindx_tb_118));
  assign idx_comp_119 = ($signed(indx) < $signed(pindx_tb_119));
  assign idx_comp_120 = ($signed(indx) < $signed(pindx_tb_120));
  assign idx_comp_121 = ($signed(indx) < $signed(pindx_tb_121));
  assign idx_comp_122 = ($signed(indx) < $signed(pindx_tb_122));
  assign idx_comp_123 = ($signed(indx) < $signed(pindx_tb_123));
  assign idx_comp_124 = ($signed(indx) < $signed(pindx_tb_124));
  assign idx_comp_125 = ($signed(indx) < $signed(pindx_tb_125));
  assign idx_comp_126 = ($signed(indx) < $signed(pindx_tb_126));
  assign idx_comp_127 = ($signed(indx) < $signed(pindx_tb_127));
  always @ (*) begin
    idx_comp_vec[0] = idx_comp_127;
    idx_comp_vec[1] = idx_comp_126;
    idx_comp_vec[2] = idx_comp_125;
    idx_comp_vec[3] = idx_comp_124;
    idx_comp_vec[4] = idx_comp_123;
    idx_comp_vec[5] = idx_comp_122;
    idx_comp_vec[6] = idx_comp_121;
    idx_comp_vec[7] = idx_comp_120;
    idx_comp_vec[8] = idx_comp_119;
    idx_comp_vec[9] = idx_comp_118;
    idx_comp_vec[10] = idx_comp_117;
    idx_comp_vec[11] = idx_comp_116;
    idx_comp_vec[12] = idx_comp_115;
    idx_comp_vec[13] = idx_comp_114;
    idx_comp_vec[14] = idx_comp_113;
    idx_comp_vec[15] = idx_comp_112;
    idx_comp_vec[16] = idx_comp_111;
    idx_comp_vec[17] = idx_comp_110;
    idx_comp_vec[18] = idx_comp_109;
    idx_comp_vec[19] = idx_comp_108;
    idx_comp_vec[20] = idx_comp_107;
    idx_comp_vec[21] = idx_comp_106;
    idx_comp_vec[22] = idx_comp_105;
    idx_comp_vec[23] = idx_comp_104;
    idx_comp_vec[24] = idx_comp_103;
    idx_comp_vec[25] = idx_comp_102;
    idx_comp_vec[26] = idx_comp_101;
    idx_comp_vec[27] = idx_comp_100;
    idx_comp_vec[28] = idx_comp_99;
    idx_comp_vec[29] = idx_comp_98;
    idx_comp_vec[30] = idx_comp_97;
    idx_comp_vec[31] = idx_comp_96;
    idx_comp_vec[32] = idx_comp_95;
    idx_comp_vec[33] = idx_comp_94;
    idx_comp_vec[34] = idx_comp_93;
    idx_comp_vec[35] = idx_comp_92;
    idx_comp_vec[36] = idx_comp_91;
    idx_comp_vec[37] = idx_comp_90;
    idx_comp_vec[38] = idx_comp_89;
    idx_comp_vec[39] = idx_comp_88;
    idx_comp_vec[40] = idx_comp_87;
    idx_comp_vec[41] = idx_comp_86;
    idx_comp_vec[42] = idx_comp_85;
    idx_comp_vec[43] = idx_comp_84;
    idx_comp_vec[44] = idx_comp_83;
    idx_comp_vec[45] = idx_comp_82;
    idx_comp_vec[46] = idx_comp_81;
    idx_comp_vec[47] = idx_comp_80;
    idx_comp_vec[48] = idx_comp_79;
    idx_comp_vec[49] = idx_comp_78;
    idx_comp_vec[50] = idx_comp_77;
    idx_comp_vec[51] = idx_comp_76;
    idx_comp_vec[52] = idx_comp_75;
    idx_comp_vec[53] = idx_comp_74;
    idx_comp_vec[54] = idx_comp_73;
    idx_comp_vec[55] = idx_comp_72;
    idx_comp_vec[56] = idx_comp_71;
    idx_comp_vec[57] = idx_comp_70;
    idx_comp_vec[58] = idx_comp_69;
    idx_comp_vec[59] = idx_comp_68;
    idx_comp_vec[60] = idx_comp_67;
    idx_comp_vec[61] = idx_comp_66;
    idx_comp_vec[62] = idx_comp_65;
    idx_comp_vec[63] = idx_comp_64;
    idx_comp_vec[64] = idx_comp_63;
    idx_comp_vec[65] = idx_comp_62;
    idx_comp_vec[66] = idx_comp_61;
    idx_comp_vec[67] = idx_comp_60;
    idx_comp_vec[68] = idx_comp_59;
    idx_comp_vec[69] = idx_comp_58;
    idx_comp_vec[70] = idx_comp_57;
    idx_comp_vec[71] = idx_comp_56;
    idx_comp_vec[72] = idx_comp_55;
    idx_comp_vec[73] = idx_comp_54;
    idx_comp_vec[74] = idx_comp_53;
    idx_comp_vec[75] = idx_comp_52;
    idx_comp_vec[76] = idx_comp_51;
    idx_comp_vec[77] = idx_comp_50;
    idx_comp_vec[78] = idx_comp_49;
    idx_comp_vec[79] = idx_comp_48;
    idx_comp_vec[80] = idx_comp_47;
    idx_comp_vec[81] = idx_comp_46;
    idx_comp_vec[82] = idx_comp_45;
    idx_comp_vec[83] = idx_comp_44;
    idx_comp_vec[84] = idx_comp_43;
    idx_comp_vec[85] = idx_comp_42;
    idx_comp_vec[86] = idx_comp_41;
    idx_comp_vec[87] = idx_comp_40;
    idx_comp_vec[88] = idx_comp_39;
    idx_comp_vec[89] = idx_comp_38;
    idx_comp_vec[90] = idx_comp_37;
    idx_comp_vec[91] = idx_comp_36;
    idx_comp_vec[92] = idx_comp_35;
    idx_comp_vec[93] = idx_comp_34;
    idx_comp_vec[94] = idx_comp_33;
    idx_comp_vec[95] = idx_comp_32;
    idx_comp_vec[96] = idx_comp_31;
    idx_comp_vec[97] = idx_comp_30;
    idx_comp_vec[98] = idx_comp_29;
    idx_comp_vec[99] = idx_comp_28;
    idx_comp_vec[100] = idx_comp_27;
    idx_comp_vec[101] = idx_comp_26;
    idx_comp_vec[102] = idx_comp_25;
    idx_comp_vec[103] = idx_comp_24;
    idx_comp_vec[104] = idx_comp_23;
    idx_comp_vec[105] = idx_comp_22;
    idx_comp_vec[106] = idx_comp_21;
    idx_comp_vec[107] = idx_comp_20;
    idx_comp_vec[108] = idx_comp_19;
    idx_comp_vec[109] = idx_comp_18;
    idx_comp_vec[110] = idx_comp_17;
    idx_comp_vec[111] = idx_comp_16;
    idx_comp_vec[112] = idx_comp_15;
    idx_comp_vec[113] = idx_comp_14;
    idx_comp_vec[114] = idx_comp_13;
    idx_comp_vec[115] = idx_comp_12;
    idx_comp_vec[116] = idx_comp_11;
    idx_comp_vec[117] = idx_comp_10;
    idx_comp_vec[118] = idx_comp_9;
    idx_comp_vec[119] = idx_comp_8;
    idx_comp_vec[120] = idx_comp_7;
    idx_comp_vec[121] = idx_comp_6;
    idx_comp_vec[122] = idx_comp_5;
    idx_comp_vec[123] = idx_comp_4;
    idx_comp_vec[124] = idx_comp_3;
    idx_comp_vec[125] = idx_comp_2;
    idx_comp_vec[126] = idx_comp_1;
    idx_comp_vec[127] = idx_comp_0;
  end

  assign x_tmp = idx_comp_vec;
  assign all_zeros = ((! (x_tmp != 128'h0)) ? 8'hff : 8'h0);
  assign all_left_zeros = ((! (x_tmp[127 : 64] != 64'h0)) ? 8'hff : 8'h0);
  assign whole_bit_count = 8'h80;
  assign left_bit_count = 8'h40;
  assign x_tmp_1 = x_tmp[63 : 0];
  assign all_zeros_1 = ((! (x_tmp_1 != 64'h0)) ? 7'h7f : 7'h0);
  assign all_left_zeros_1 = ((! (x_tmp_1[63 : 32] != 32'h0)) ? 7'h7f : 7'h0);
  assign whole_bit_count_1 = 7'h40;
  assign left_bit_count_1 = 7'h20;
  assign x_tmp_2 = x_tmp_1[31 : 0];
  assign all_zeros_2 = ((! (x_tmp_2 != 32'h0)) ? 6'h3f : 6'h0);
  assign all_left_zeros_2 = ((! (x_tmp_2[31 : 16] != 16'h0)) ? 6'h3f : 6'h0);
  assign whole_bit_count_2 = 6'h20;
  assign left_bit_count_2 = 6'h10;
  assign x_tmp_3 = x_tmp_2[15 : 0];
  assign all_zeros_3 = ((! (x_tmp_3 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_3 = ((! (x_tmp_3[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_3 = 5'h10;
  assign left_bit_count_3 = 5'h08;
  assign x_tmp_4 = x_tmp_3[7 : 0];
  assign all_zeros_4 = ((! (x_tmp_4 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_4 = ((! (x_tmp_4[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_4 = 4'b1000;
  assign left_bit_count_4 = 4'b0100;
  assign x_tmp_5 = x_tmp_4[3 : 0];
  assign all_zeros_5 = ((! (x_tmp_5 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_5 = ((! (x_tmp_5[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_5 = 3'b100;
  assign left_bit_count_5 = 3'b010;
  assign x_tmp_6 = x_tmp_5[1 : 0];
  assign all_zeros_6 = ((! (x_tmp_6 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_6 = ((! (x_tmp_6[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_6 = 2'b10;
  assign left_bit_count_6 = 2'b01;
  assign _zz_2 = x_tmp_6[0 : 0];
  assign x_tmp_7 = _zz_2;
  assign all_zeros_7 = ((! (x_tmp_7 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_7 = ((! (x_tmp_7[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count = (all_left_zeros_6 & _zz_157);
  assign _zz_3 = x_tmp_6[1 : 1];
  assign x_tmp_8 = _zz_3;
  assign all_zeros_8 = ((! (x_tmp_8 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_8 = ((! (x_tmp_8[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count = ((~ all_left_zeros_6) & _zz_161);
  assign non_zeros_lzc = (left_all_zeros_count + left_non_all_zeros_count);
  assign ret_6 = ((all_zeros_6 & whole_bit_count_6) + ((~ all_zeros_6) & non_zeros_lzc));
  assign left_all_zeros_count_1 = (all_left_zeros_5 & _zz_162);
  assign x_tmp_9 = x_tmp_5[3 : 2];
  assign all_zeros_9 = ((! (x_tmp_9 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_9 = ((! (x_tmp_9[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_7 = 2'b10;
  assign left_bit_count_7 = 2'b01;
  assign _zz_4 = x_tmp_9[0 : 0];
  assign x_tmp_10 = _zz_4;
  assign all_zeros_10 = ((! (x_tmp_10 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_10 = ((! (x_tmp_10[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_2 = (all_left_zeros_9 & _zz_164);
  assign _zz_5 = x_tmp_9[1 : 1];
  assign x_tmp_11 = _zz_5;
  assign all_zeros_11 = ((! (x_tmp_11 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_11 = ((! (x_tmp_11[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_1 = ((~ all_left_zeros_9) & _zz_168);
  assign non_zeros_lzc_1 = (left_all_zeros_count_2 + left_non_all_zeros_count_1);
  assign ret_9 = ((all_zeros_9 & whole_bit_count_7) + ((~ all_zeros_9) & non_zeros_lzc_1));
  assign left_non_all_zeros_count_2 = ((~ all_left_zeros_5) & _zz_169);
  assign non_zeros_lzc_2 = (left_all_zeros_count_1 + left_non_all_zeros_count_2);
  assign ret_5 = ((all_zeros_5 & whole_bit_count_5) + ((~ all_zeros_5) & non_zeros_lzc_2));
  assign left_all_zeros_count_3 = (all_left_zeros_4 & _zz_170);
  assign x_tmp_12 = x_tmp_4[7 : 4];
  assign all_zeros_12 = ((! (x_tmp_12 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_12 = ((! (x_tmp_12[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_8 = 3'b100;
  assign left_bit_count_8 = 3'b010;
  assign x_tmp_13 = x_tmp_12[1 : 0];
  assign all_zeros_13 = ((! (x_tmp_13 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_13 = ((! (x_tmp_13[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_9 = 2'b10;
  assign left_bit_count_9 = 2'b01;
  assign _zz_6 = x_tmp_13[0 : 0];
  assign x_tmp_14 = _zz_6;
  assign all_zeros_14 = ((! (x_tmp_14 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_14 = ((! (x_tmp_14[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_4 = (all_left_zeros_13 & _zz_172);
  assign _zz_7 = x_tmp_13[1 : 1];
  assign x_tmp_15 = _zz_7;
  assign all_zeros_15 = ((! (x_tmp_15 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_15 = ((! (x_tmp_15[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_3 = ((~ all_left_zeros_13) & _zz_176);
  assign non_zeros_lzc_3 = (left_all_zeros_count_4 + left_non_all_zeros_count_3);
  assign ret_13 = ((all_zeros_13 & whole_bit_count_9) + ((~ all_zeros_13) & non_zeros_lzc_3));
  assign left_all_zeros_count_5 = (all_left_zeros_12 & _zz_177);
  assign x_tmp_16 = x_tmp_12[3 : 2];
  assign all_zeros_16 = ((! (x_tmp_16 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_16 = ((! (x_tmp_16[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_10 = 2'b10;
  assign left_bit_count_10 = 2'b01;
  assign _zz_8 = x_tmp_16[0 : 0];
  assign x_tmp_17 = _zz_8;
  assign all_zeros_17 = ((! (x_tmp_17 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_17 = ((! (x_tmp_17[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_6 = (all_left_zeros_16 & _zz_179);
  assign _zz_9 = x_tmp_16[1 : 1];
  assign x_tmp_18 = _zz_9;
  assign all_zeros_18 = ((! (x_tmp_18 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_18 = ((! (x_tmp_18[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_4 = ((~ all_left_zeros_16) & _zz_183);
  assign non_zeros_lzc_4 = (left_all_zeros_count_6 + left_non_all_zeros_count_4);
  assign ret_16 = ((all_zeros_16 & whole_bit_count_10) + ((~ all_zeros_16) & non_zeros_lzc_4));
  assign left_non_all_zeros_count_5 = ((~ all_left_zeros_12) & _zz_184);
  assign non_zeros_lzc_5 = (left_all_zeros_count_5 + left_non_all_zeros_count_5);
  assign ret_12 = ((all_zeros_12 & whole_bit_count_8) + ((~ all_zeros_12) & non_zeros_lzc_5));
  assign left_non_all_zeros_count_6 = ((~ all_left_zeros_4) & _zz_185);
  assign non_zeros_lzc_6 = (left_all_zeros_count_3 + left_non_all_zeros_count_6);
  assign ret_4 = ((all_zeros_4 & whole_bit_count_4) + ((~ all_zeros_4) & non_zeros_lzc_6));
  assign left_all_zeros_count_7 = (all_left_zeros_3 & _zz_186);
  assign x_tmp_19 = x_tmp_3[15 : 8];
  assign all_zeros_19 = ((! (x_tmp_19 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_19 = ((! (x_tmp_19[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_11 = 4'b1000;
  assign left_bit_count_11 = 4'b0100;
  assign x_tmp_20 = x_tmp_19[3 : 0];
  assign all_zeros_20 = ((! (x_tmp_20 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_20 = ((! (x_tmp_20[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_12 = 3'b100;
  assign left_bit_count_12 = 3'b010;
  assign x_tmp_21 = x_tmp_20[1 : 0];
  assign all_zeros_21 = ((! (x_tmp_21 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_21 = ((! (x_tmp_21[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_13 = 2'b10;
  assign left_bit_count_13 = 2'b01;
  assign _zz_10 = x_tmp_21[0 : 0];
  assign x_tmp_22 = _zz_10;
  assign all_zeros_22 = ((! (x_tmp_22 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_22 = ((! (x_tmp_22[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_8 = (all_left_zeros_21 & _zz_188);
  assign _zz_11 = x_tmp_21[1 : 1];
  assign x_tmp_23 = _zz_11;
  assign all_zeros_23 = ((! (x_tmp_23 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_23 = ((! (x_tmp_23[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_7 = ((~ all_left_zeros_21) & _zz_192);
  assign non_zeros_lzc_7 = (left_all_zeros_count_8 + left_non_all_zeros_count_7);
  assign ret_21 = ((all_zeros_21 & whole_bit_count_13) + ((~ all_zeros_21) & non_zeros_lzc_7));
  assign left_all_zeros_count_9 = (all_left_zeros_20 & _zz_193);
  assign x_tmp_24 = x_tmp_20[3 : 2];
  assign all_zeros_24 = ((! (x_tmp_24 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_24 = ((! (x_tmp_24[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_14 = 2'b10;
  assign left_bit_count_14 = 2'b01;
  assign _zz_12 = x_tmp_24[0 : 0];
  assign x_tmp_25 = _zz_12;
  assign all_zeros_25 = ((! (x_tmp_25 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_25 = ((! (x_tmp_25[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_10 = (all_left_zeros_24 & _zz_195);
  assign _zz_13 = x_tmp_24[1 : 1];
  assign x_tmp_26 = _zz_13;
  assign all_zeros_26 = ((! (x_tmp_26 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_26 = ((! (x_tmp_26[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_8 = ((~ all_left_zeros_24) & _zz_199);
  assign non_zeros_lzc_8 = (left_all_zeros_count_10 + left_non_all_zeros_count_8);
  assign ret_24 = ((all_zeros_24 & whole_bit_count_14) + ((~ all_zeros_24) & non_zeros_lzc_8));
  assign left_non_all_zeros_count_9 = ((~ all_left_zeros_20) & _zz_200);
  assign non_zeros_lzc_9 = (left_all_zeros_count_9 + left_non_all_zeros_count_9);
  assign ret_20 = ((all_zeros_20 & whole_bit_count_12) + ((~ all_zeros_20) & non_zeros_lzc_9));
  assign left_all_zeros_count_11 = (all_left_zeros_19 & _zz_201);
  assign x_tmp_27 = x_tmp_19[7 : 4];
  assign all_zeros_27 = ((! (x_tmp_27 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_27 = ((! (x_tmp_27[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_15 = 3'b100;
  assign left_bit_count_15 = 3'b010;
  assign x_tmp_28 = x_tmp_27[1 : 0];
  assign all_zeros_28 = ((! (x_tmp_28 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_28 = ((! (x_tmp_28[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_16 = 2'b10;
  assign left_bit_count_16 = 2'b01;
  assign _zz_14 = x_tmp_28[0 : 0];
  assign x_tmp_29 = _zz_14;
  assign all_zeros_29 = ((! (x_tmp_29 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_29 = ((! (x_tmp_29[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_12 = (all_left_zeros_28 & _zz_203);
  assign _zz_15 = x_tmp_28[1 : 1];
  assign x_tmp_30 = _zz_15;
  assign all_zeros_30 = ((! (x_tmp_30 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_30 = ((! (x_tmp_30[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_10 = ((~ all_left_zeros_28) & _zz_207);
  assign non_zeros_lzc_10 = (left_all_zeros_count_12 + left_non_all_zeros_count_10);
  assign ret_28 = ((all_zeros_28 & whole_bit_count_16) + ((~ all_zeros_28) & non_zeros_lzc_10));
  assign left_all_zeros_count_13 = (all_left_zeros_27 & _zz_208);
  assign x_tmp_31 = x_tmp_27[3 : 2];
  assign all_zeros_31 = ((! (x_tmp_31 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_31 = ((! (x_tmp_31[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_17 = 2'b10;
  assign left_bit_count_17 = 2'b01;
  assign _zz_16 = x_tmp_31[0 : 0];
  assign x_tmp_32 = _zz_16;
  assign all_zeros_32 = ((! (x_tmp_32 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_32 = ((! (x_tmp_32[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_14 = (all_left_zeros_31 & _zz_210);
  assign _zz_17 = x_tmp_31[1 : 1];
  assign x_tmp_33 = _zz_17;
  assign all_zeros_33 = ((! (x_tmp_33 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_33 = ((! (x_tmp_33[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_11 = ((~ all_left_zeros_31) & _zz_214);
  assign non_zeros_lzc_11 = (left_all_zeros_count_14 + left_non_all_zeros_count_11);
  assign ret_31 = ((all_zeros_31 & whole_bit_count_17) + ((~ all_zeros_31) & non_zeros_lzc_11));
  assign left_non_all_zeros_count_12 = ((~ all_left_zeros_27) & _zz_215);
  assign non_zeros_lzc_12 = (left_all_zeros_count_13 + left_non_all_zeros_count_12);
  assign ret_27 = ((all_zeros_27 & whole_bit_count_15) + ((~ all_zeros_27) & non_zeros_lzc_12));
  assign left_non_all_zeros_count_13 = ((~ all_left_zeros_19) & _zz_216);
  assign non_zeros_lzc_13 = (left_all_zeros_count_11 + left_non_all_zeros_count_13);
  assign ret_19 = ((all_zeros_19 & whole_bit_count_11) + ((~ all_zeros_19) & non_zeros_lzc_13));
  assign left_non_all_zeros_count_14 = ((~ all_left_zeros_3) & _zz_217);
  assign non_zeros_lzc_14 = (left_all_zeros_count_7 + left_non_all_zeros_count_14);
  assign ret_3 = ((all_zeros_3 & whole_bit_count_3) + ((~ all_zeros_3) & non_zeros_lzc_14));
  assign left_all_zeros_count_15 = (all_left_zeros_2 & _zz_218);
  assign x_tmp_34 = x_tmp_2[31 : 16];
  assign all_zeros_34 = ((! (x_tmp_34 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_34 = ((! (x_tmp_34[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_18 = 5'h10;
  assign left_bit_count_18 = 5'h08;
  assign x_tmp_35 = x_tmp_34[7 : 0];
  assign all_zeros_35 = ((! (x_tmp_35 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_35 = ((! (x_tmp_35[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_19 = 4'b1000;
  assign left_bit_count_19 = 4'b0100;
  assign x_tmp_36 = x_tmp_35[3 : 0];
  assign all_zeros_36 = ((! (x_tmp_36 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_36 = ((! (x_tmp_36[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_20 = 3'b100;
  assign left_bit_count_20 = 3'b010;
  assign x_tmp_37 = x_tmp_36[1 : 0];
  assign all_zeros_37 = ((! (x_tmp_37 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_37 = ((! (x_tmp_37[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_21 = 2'b10;
  assign left_bit_count_21 = 2'b01;
  assign _zz_18 = x_tmp_37[0 : 0];
  assign x_tmp_38 = _zz_18;
  assign all_zeros_38 = ((! (x_tmp_38 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_38 = ((! (x_tmp_38[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_16 = (all_left_zeros_37 & _zz_220);
  assign _zz_19 = x_tmp_37[1 : 1];
  assign x_tmp_39 = _zz_19;
  assign all_zeros_39 = ((! (x_tmp_39 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_39 = ((! (x_tmp_39[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_15 = ((~ all_left_zeros_37) & _zz_224);
  assign non_zeros_lzc_15 = (left_all_zeros_count_16 + left_non_all_zeros_count_15);
  assign ret_37 = ((all_zeros_37 & whole_bit_count_21) + ((~ all_zeros_37) & non_zeros_lzc_15));
  assign left_all_zeros_count_17 = (all_left_zeros_36 & _zz_225);
  assign x_tmp_40 = x_tmp_36[3 : 2];
  assign all_zeros_40 = ((! (x_tmp_40 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_40 = ((! (x_tmp_40[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_22 = 2'b10;
  assign left_bit_count_22 = 2'b01;
  assign _zz_20 = x_tmp_40[0 : 0];
  assign x_tmp_41 = _zz_20;
  assign all_zeros_41 = ((! (x_tmp_41 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_41 = ((! (x_tmp_41[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_18 = (all_left_zeros_40 & _zz_227);
  assign _zz_21 = x_tmp_40[1 : 1];
  assign x_tmp_42 = _zz_21;
  assign all_zeros_42 = ((! (x_tmp_42 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_42 = ((! (x_tmp_42[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_16 = ((~ all_left_zeros_40) & _zz_231);
  assign non_zeros_lzc_16 = (left_all_zeros_count_18 + left_non_all_zeros_count_16);
  assign ret_40 = ((all_zeros_40 & whole_bit_count_22) + ((~ all_zeros_40) & non_zeros_lzc_16));
  assign left_non_all_zeros_count_17 = ((~ all_left_zeros_36) & _zz_232);
  assign non_zeros_lzc_17 = (left_all_zeros_count_17 + left_non_all_zeros_count_17);
  assign ret_36 = ((all_zeros_36 & whole_bit_count_20) + ((~ all_zeros_36) & non_zeros_lzc_17));
  assign left_all_zeros_count_19 = (all_left_zeros_35 & _zz_233);
  assign x_tmp_43 = x_tmp_35[7 : 4];
  assign all_zeros_43 = ((! (x_tmp_43 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_43 = ((! (x_tmp_43[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_23 = 3'b100;
  assign left_bit_count_23 = 3'b010;
  assign x_tmp_44 = x_tmp_43[1 : 0];
  assign all_zeros_44 = ((! (x_tmp_44 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_44 = ((! (x_tmp_44[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_24 = 2'b10;
  assign left_bit_count_24 = 2'b01;
  assign _zz_22 = x_tmp_44[0 : 0];
  assign x_tmp_45 = _zz_22;
  assign all_zeros_45 = ((! (x_tmp_45 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_45 = ((! (x_tmp_45[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_20 = (all_left_zeros_44 & _zz_235);
  assign _zz_23 = x_tmp_44[1 : 1];
  assign x_tmp_46 = _zz_23;
  assign all_zeros_46 = ((! (x_tmp_46 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_46 = ((! (x_tmp_46[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_18 = ((~ all_left_zeros_44) & _zz_239);
  assign non_zeros_lzc_18 = (left_all_zeros_count_20 + left_non_all_zeros_count_18);
  assign ret_44 = ((all_zeros_44 & whole_bit_count_24) + ((~ all_zeros_44) & non_zeros_lzc_18));
  assign left_all_zeros_count_21 = (all_left_zeros_43 & _zz_240);
  assign x_tmp_47 = x_tmp_43[3 : 2];
  assign all_zeros_47 = ((! (x_tmp_47 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_47 = ((! (x_tmp_47[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_25 = 2'b10;
  assign left_bit_count_25 = 2'b01;
  assign _zz_24 = x_tmp_47[0 : 0];
  assign x_tmp_48 = _zz_24;
  assign all_zeros_48 = ((! (x_tmp_48 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_48 = ((! (x_tmp_48[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_22 = (all_left_zeros_47 & _zz_242);
  assign _zz_25 = x_tmp_47[1 : 1];
  assign x_tmp_49 = _zz_25;
  assign all_zeros_49 = ((! (x_tmp_49 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_49 = ((! (x_tmp_49[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_19 = ((~ all_left_zeros_47) & _zz_246);
  assign non_zeros_lzc_19 = (left_all_zeros_count_22 + left_non_all_zeros_count_19);
  assign ret_47 = ((all_zeros_47 & whole_bit_count_25) + ((~ all_zeros_47) & non_zeros_lzc_19));
  assign left_non_all_zeros_count_20 = ((~ all_left_zeros_43) & _zz_247);
  assign non_zeros_lzc_20 = (left_all_zeros_count_21 + left_non_all_zeros_count_20);
  assign ret_43 = ((all_zeros_43 & whole_bit_count_23) + ((~ all_zeros_43) & non_zeros_lzc_20));
  assign left_non_all_zeros_count_21 = ((~ all_left_zeros_35) & _zz_248);
  assign non_zeros_lzc_21 = (left_all_zeros_count_19 + left_non_all_zeros_count_21);
  assign ret_35 = ((all_zeros_35 & whole_bit_count_19) + ((~ all_zeros_35) & non_zeros_lzc_21));
  assign left_all_zeros_count_23 = (all_left_zeros_34 & _zz_249);
  assign x_tmp_50 = x_tmp_34[15 : 8];
  assign all_zeros_50 = ((! (x_tmp_50 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_50 = ((! (x_tmp_50[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_26 = 4'b1000;
  assign left_bit_count_26 = 4'b0100;
  assign x_tmp_51 = x_tmp_50[3 : 0];
  assign all_zeros_51 = ((! (x_tmp_51 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_51 = ((! (x_tmp_51[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_27 = 3'b100;
  assign left_bit_count_27 = 3'b010;
  assign x_tmp_52 = x_tmp_51[1 : 0];
  assign all_zeros_52 = ((! (x_tmp_52 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_52 = ((! (x_tmp_52[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_28 = 2'b10;
  assign left_bit_count_28 = 2'b01;
  assign _zz_26 = x_tmp_52[0 : 0];
  assign x_tmp_53 = _zz_26;
  assign all_zeros_53 = ((! (x_tmp_53 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_53 = ((! (x_tmp_53[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_24 = (all_left_zeros_52 & _zz_251);
  assign _zz_27 = x_tmp_52[1 : 1];
  assign x_tmp_54 = _zz_27;
  assign all_zeros_54 = ((! (x_tmp_54 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_54 = ((! (x_tmp_54[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_22 = ((~ all_left_zeros_52) & _zz_255);
  assign non_zeros_lzc_22 = (left_all_zeros_count_24 + left_non_all_zeros_count_22);
  assign ret_52 = ((all_zeros_52 & whole_bit_count_28) + ((~ all_zeros_52) & non_zeros_lzc_22));
  assign left_all_zeros_count_25 = (all_left_zeros_51 & _zz_256);
  assign x_tmp_55 = x_tmp_51[3 : 2];
  assign all_zeros_55 = ((! (x_tmp_55 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_55 = ((! (x_tmp_55[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_29 = 2'b10;
  assign left_bit_count_29 = 2'b01;
  assign _zz_28 = x_tmp_55[0 : 0];
  assign x_tmp_56 = _zz_28;
  assign all_zeros_56 = ((! (x_tmp_56 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_56 = ((! (x_tmp_56[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_26 = (all_left_zeros_55 & _zz_258);
  assign _zz_29 = x_tmp_55[1 : 1];
  assign x_tmp_57 = _zz_29;
  assign all_zeros_57 = ((! (x_tmp_57 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_57 = ((! (x_tmp_57[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_23 = ((~ all_left_zeros_55) & _zz_262);
  assign non_zeros_lzc_23 = (left_all_zeros_count_26 + left_non_all_zeros_count_23);
  assign ret_55 = ((all_zeros_55 & whole_bit_count_29) + ((~ all_zeros_55) & non_zeros_lzc_23));
  assign left_non_all_zeros_count_24 = ((~ all_left_zeros_51) & _zz_263);
  assign non_zeros_lzc_24 = (left_all_zeros_count_25 + left_non_all_zeros_count_24);
  assign ret_51 = ((all_zeros_51 & whole_bit_count_27) + ((~ all_zeros_51) & non_zeros_lzc_24));
  assign left_all_zeros_count_27 = (all_left_zeros_50 & _zz_264);
  assign x_tmp_58 = x_tmp_50[7 : 4];
  assign all_zeros_58 = ((! (x_tmp_58 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_58 = ((! (x_tmp_58[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_30 = 3'b100;
  assign left_bit_count_30 = 3'b010;
  assign x_tmp_59 = x_tmp_58[1 : 0];
  assign all_zeros_59 = ((! (x_tmp_59 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_59 = ((! (x_tmp_59[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_31 = 2'b10;
  assign left_bit_count_31 = 2'b01;
  assign _zz_30 = x_tmp_59[0 : 0];
  assign x_tmp_60 = _zz_30;
  assign all_zeros_60 = ((! (x_tmp_60 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_60 = ((! (x_tmp_60[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_28 = (all_left_zeros_59 & _zz_266);
  assign _zz_31 = x_tmp_59[1 : 1];
  assign x_tmp_61 = _zz_31;
  assign all_zeros_61 = ((! (x_tmp_61 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_61 = ((! (x_tmp_61[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_25 = ((~ all_left_zeros_59) & _zz_270);
  assign non_zeros_lzc_25 = (left_all_zeros_count_28 + left_non_all_zeros_count_25);
  assign ret_59 = ((all_zeros_59 & whole_bit_count_31) + ((~ all_zeros_59) & non_zeros_lzc_25));
  assign left_all_zeros_count_29 = (all_left_zeros_58 & _zz_271);
  assign x_tmp_62 = x_tmp_58[3 : 2];
  assign all_zeros_62 = ((! (x_tmp_62 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_62 = ((! (x_tmp_62[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_32 = 2'b10;
  assign left_bit_count_32 = 2'b01;
  assign _zz_32 = x_tmp_62[0 : 0];
  assign x_tmp_63 = _zz_32;
  assign all_zeros_63 = ((! (x_tmp_63 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_63 = ((! (x_tmp_63[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_30 = (all_left_zeros_62 & _zz_273);
  assign _zz_33 = x_tmp_62[1 : 1];
  assign x_tmp_64 = _zz_33;
  assign all_zeros_64 = ((! (x_tmp_64 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_64 = ((! (x_tmp_64[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_26 = ((~ all_left_zeros_62) & _zz_277);
  assign non_zeros_lzc_26 = (left_all_zeros_count_30 + left_non_all_zeros_count_26);
  assign ret_62 = ((all_zeros_62 & whole_bit_count_32) + ((~ all_zeros_62) & non_zeros_lzc_26));
  assign left_non_all_zeros_count_27 = ((~ all_left_zeros_58) & _zz_278);
  assign non_zeros_lzc_27 = (left_all_zeros_count_29 + left_non_all_zeros_count_27);
  assign ret_58 = ((all_zeros_58 & whole_bit_count_30) + ((~ all_zeros_58) & non_zeros_lzc_27));
  assign left_non_all_zeros_count_28 = ((~ all_left_zeros_50) & _zz_279);
  assign non_zeros_lzc_28 = (left_all_zeros_count_27 + left_non_all_zeros_count_28);
  assign ret_50 = ((all_zeros_50 & whole_bit_count_26) + ((~ all_zeros_50) & non_zeros_lzc_28));
  assign left_non_all_zeros_count_29 = ((~ all_left_zeros_34) & _zz_280);
  assign non_zeros_lzc_29 = (left_all_zeros_count_23 + left_non_all_zeros_count_29);
  assign ret_34 = ((all_zeros_34 & whole_bit_count_18) + ((~ all_zeros_34) & non_zeros_lzc_29));
  assign left_non_all_zeros_count_30 = ((~ all_left_zeros_2) & _zz_281);
  assign non_zeros_lzc_30 = (left_all_zeros_count_15 + left_non_all_zeros_count_30);
  assign ret_2 = ((all_zeros_2 & whole_bit_count_2) + ((~ all_zeros_2) & non_zeros_lzc_30));
  assign left_all_zeros_count_31 = (all_left_zeros_1 & _zz_282);
  assign x_tmp_65 = x_tmp_1[63 : 32];
  assign all_zeros_65 = ((! (x_tmp_65 != 32'h0)) ? 6'h3f : 6'h0);
  assign all_left_zeros_65 = ((! (x_tmp_65[31 : 16] != 16'h0)) ? 6'h3f : 6'h0);
  assign whole_bit_count_33 = 6'h20;
  assign left_bit_count_33 = 6'h10;
  assign x_tmp_66 = x_tmp_65[15 : 0];
  assign all_zeros_66 = ((! (x_tmp_66 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_66 = ((! (x_tmp_66[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_34 = 5'h10;
  assign left_bit_count_34 = 5'h08;
  assign x_tmp_67 = x_tmp_66[7 : 0];
  assign all_zeros_67 = ((! (x_tmp_67 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_67 = ((! (x_tmp_67[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_35 = 4'b1000;
  assign left_bit_count_35 = 4'b0100;
  assign x_tmp_68 = x_tmp_67[3 : 0];
  assign all_zeros_68 = ((! (x_tmp_68 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_68 = ((! (x_tmp_68[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_36 = 3'b100;
  assign left_bit_count_36 = 3'b010;
  assign x_tmp_69 = x_tmp_68[1 : 0];
  assign all_zeros_69 = ((! (x_tmp_69 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_69 = ((! (x_tmp_69[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_37 = 2'b10;
  assign left_bit_count_37 = 2'b01;
  assign _zz_34 = x_tmp_69[0 : 0];
  assign x_tmp_70 = _zz_34;
  assign all_zeros_70 = ((! (x_tmp_70 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_70 = ((! (x_tmp_70[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_32 = (all_left_zeros_69 & _zz_284);
  assign _zz_35 = x_tmp_69[1 : 1];
  assign x_tmp_71 = _zz_35;
  assign all_zeros_71 = ((! (x_tmp_71 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_71 = ((! (x_tmp_71[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_31 = ((~ all_left_zeros_69) & _zz_288);
  assign non_zeros_lzc_31 = (left_all_zeros_count_32 + left_non_all_zeros_count_31);
  assign ret_69 = ((all_zeros_69 & whole_bit_count_37) + ((~ all_zeros_69) & non_zeros_lzc_31));
  assign left_all_zeros_count_33 = (all_left_zeros_68 & _zz_289);
  assign x_tmp_72 = x_tmp_68[3 : 2];
  assign all_zeros_72 = ((! (x_tmp_72 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_72 = ((! (x_tmp_72[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_38 = 2'b10;
  assign left_bit_count_38 = 2'b01;
  assign _zz_36 = x_tmp_72[0 : 0];
  assign x_tmp_73 = _zz_36;
  assign all_zeros_73 = ((! (x_tmp_73 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_73 = ((! (x_tmp_73[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_34 = (all_left_zeros_72 & _zz_291);
  assign _zz_37 = x_tmp_72[1 : 1];
  assign x_tmp_74 = _zz_37;
  assign all_zeros_74 = ((! (x_tmp_74 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_74 = ((! (x_tmp_74[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_32 = ((~ all_left_zeros_72) & _zz_295);
  assign non_zeros_lzc_32 = (left_all_zeros_count_34 + left_non_all_zeros_count_32);
  assign ret_72 = ((all_zeros_72 & whole_bit_count_38) + ((~ all_zeros_72) & non_zeros_lzc_32));
  assign left_non_all_zeros_count_33 = ((~ all_left_zeros_68) & _zz_296);
  assign non_zeros_lzc_33 = (left_all_zeros_count_33 + left_non_all_zeros_count_33);
  assign ret_68 = ((all_zeros_68 & whole_bit_count_36) + ((~ all_zeros_68) & non_zeros_lzc_33));
  assign left_all_zeros_count_35 = (all_left_zeros_67 & _zz_297);
  assign x_tmp_75 = x_tmp_67[7 : 4];
  assign all_zeros_75 = ((! (x_tmp_75 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_75 = ((! (x_tmp_75[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_39 = 3'b100;
  assign left_bit_count_39 = 3'b010;
  assign x_tmp_76 = x_tmp_75[1 : 0];
  assign all_zeros_76 = ((! (x_tmp_76 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_76 = ((! (x_tmp_76[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_40 = 2'b10;
  assign left_bit_count_40 = 2'b01;
  assign _zz_38 = x_tmp_76[0 : 0];
  assign x_tmp_77 = _zz_38;
  assign all_zeros_77 = ((! (x_tmp_77 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_77 = ((! (x_tmp_77[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_36 = (all_left_zeros_76 & _zz_299);
  assign _zz_39 = x_tmp_76[1 : 1];
  assign x_tmp_78 = _zz_39;
  assign all_zeros_78 = ((! (x_tmp_78 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_78 = ((! (x_tmp_78[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_34 = ((~ all_left_zeros_76) & _zz_303);
  assign non_zeros_lzc_34 = (left_all_zeros_count_36 + left_non_all_zeros_count_34);
  assign ret_76 = ((all_zeros_76 & whole_bit_count_40) + ((~ all_zeros_76) & non_zeros_lzc_34));
  assign left_all_zeros_count_37 = (all_left_zeros_75 & _zz_304);
  assign x_tmp_79 = x_tmp_75[3 : 2];
  assign all_zeros_79 = ((! (x_tmp_79 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_79 = ((! (x_tmp_79[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_41 = 2'b10;
  assign left_bit_count_41 = 2'b01;
  assign _zz_40 = x_tmp_79[0 : 0];
  assign x_tmp_80 = _zz_40;
  assign all_zeros_80 = ((! (x_tmp_80 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_80 = ((! (x_tmp_80[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_38 = (all_left_zeros_79 & _zz_306);
  assign _zz_41 = x_tmp_79[1 : 1];
  assign x_tmp_81 = _zz_41;
  assign all_zeros_81 = ((! (x_tmp_81 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_81 = ((! (x_tmp_81[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_35 = ((~ all_left_zeros_79) & _zz_310);
  assign non_zeros_lzc_35 = (left_all_zeros_count_38 + left_non_all_zeros_count_35);
  assign ret_79 = ((all_zeros_79 & whole_bit_count_41) + ((~ all_zeros_79) & non_zeros_lzc_35));
  assign left_non_all_zeros_count_36 = ((~ all_left_zeros_75) & _zz_311);
  assign non_zeros_lzc_36 = (left_all_zeros_count_37 + left_non_all_zeros_count_36);
  assign ret_75 = ((all_zeros_75 & whole_bit_count_39) + ((~ all_zeros_75) & non_zeros_lzc_36));
  assign left_non_all_zeros_count_37 = ((~ all_left_zeros_67) & _zz_312);
  assign non_zeros_lzc_37 = (left_all_zeros_count_35 + left_non_all_zeros_count_37);
  assign ret_67 = ((all_zeros_67 & whole_bit_count_35) + ((~ all_zeros_67) & non_zeros_lzc_37));
  assign left_all_zeros_count_39 = (all_left_zeros_66 & _zz_313);
  assign x_tmp_82 = x_tmp_66[15 : 8];
  assign all_zeros_82 = ((! (x_tmp_82 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_82 = ((! (x_tmp_82[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_42 = 4'b1000;
  assign left_bit_count_42 = 4'b0100;
  assign x_tmp_83 = x_tmp_82[3 : 0];
  assign all_zeros_83 = ((! (x_tmp_83 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_83 = ((! (x_tmp_83[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_43 = 3'b100;
  assign left_bit_count_43 = 3'b010;
  assign x_tmp_84 = x_tmp_83[1 : 0];
  assign all_zeros_84 = ((! (x_tmp_84 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_84 = ((! (x_tmp_84[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_44 = 2'b10;
  assign left_bit_count_44 = 2'b01;
  assign _zz_42 = x_tmp_84[0 : 0];
  assign x_tmp_85 = _zz_42;
  assign all_zeros_85 = ((! (x_tmp_85 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_85 = ((! (x_tmp_85[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_40 = (all_left_zeros_84 & _zz_315);
  assign _zz_43 = x_tmp_84[1 : 1];
  assign x_tmp_86 = _zz_43;
  assign all_zeros_86 = ((! (x_tmp_86 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_86 = ((! (x_tmp_86[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_38 = ((~ all_left_zeros_84) & _zz_319);
  assign non_zeros_lzc_38 = (left_all_zeros_count_40 + left_non_all_zeros_count_38);
  assign ret_84 = ((all_zeros_84 & whole_bit_count_44) + ((~ all_zeros_84) & non_zeros_lzc_38));
  assign left_all_zeros_count_41 = (all_left_zeros_83 & _zz_320);
  assign x_tmp_87 = x_tmp_83[3 : 2];
  assign all_zeros_87 = ((! (x_tmp_87 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_87 = ((! (x_tmp_87[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_45 = 2'b10;
  assign left_bit_count_45 = 2'b01;
  assign _zz_44 = x_tmp_87[0 : 0];
  assign x_tmp_88 = _zz_44;
  assign all_zeros_88 = ((! (x_tmp_88 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_88 = ((! (x_tmp_88[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_42 = (all_left_zeros_87 & _zz_322);
  assign _zz_45 = x_tmp_87[1 : 1];
  assign x_tmp_89 = _zz_45;
  assign all_zeros_89 = ((! (x_tmp_89 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_89 = ((! (x_tmp_89[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_39 = ((~ all_left_zeros_87) & _zz_326);
  assign non_zeros_lzc_39 = (left_all_zeros_count_42 + left_non_all_zeros_count_39);
  assign ret_87 = ((all_zeros_87 & whole_bit_count_45) + ((~ all_zeros_87) & non_zeros_lzc_39));
  assign left_non_all_zeros_count_40 = ((~ all_left_zeros_83) & _zz_327);
  assign non_zeros_lzc_40 = (left_all_zeros_count_41 + left_non_all_zeros_count_40);
  assign ret_83 = ((all_zeros_83 & whole_bit_count_43) + ((~ all_zeros_83) & non_zeros_lzc_40));
  assign left_all_zeros_count_43 = (all_left_zeros_82 & _zz_328);
  assign x_tmp_90 = x_tmp_82[7 : 4];
  assign all_zeros_90 = ((! (x_tmp_90 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_90 = ((! (x_tmp_90[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_46 = 3'b100;
  assign left_bit_count_46 = 3'b010;
  assign x_tmp_91 = x_tmp_90[1 : 0];
  assign all_zeros_91 = ((! (x_tmp_91 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_91 = ((! (x_tmp_91[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_47 = 2'b10;
  assign left_bit_count_47 = 2'b01;
  assign _zz_46 = x_tmp_91[0 : 0];
  assign x_tmp_92 = _zz_46;
  assign all_zeros_92 = ((! (x_tmp_92 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_92 = ((! (x_tmp_92[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_44 = (all_left_zeros_91 & _zz_330);
  assign _zz_47 = x_tmp_91[1 : 1];
  assign x_tmp_93 = _zz_47;
  assign all_zeros_93 = ((! (x_tmp_93 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_93 = ((! (x_tmp_93[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_41 = ((~ all_left_zeros_91) & _zz_334);
  assign non_zeros_lzc_41 = (left_all_zeros_count_44 + left_non_all_zeros_count_41);
  assign ret_91 = ((all_zeros_91 & whole_bit_count_47) + ((~ all_zeros_91) & non_zeros_lzc_41));
  assign left_all_zeros_count_45 = (all_left_zeros_90 & _zz_335);
  assign x_tmp_94 = x_tmp_90[3 : 2];
  assign all_zeros_94 = ((! (x_tmp_94 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_94 = ((! (x_tmp_94[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_48 = 2'b10;
  assign left_bit_count_48 = 2'b01;
  assign _zz_48 = x_tmp_94[0 : 0];
  assign x_tmp_95 = _zz_48;
  assign all_zeros_95 = ((! (x_tmp_95 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_95 = ((! (x_tmp_95[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_46 = (all_left_zeros_94 & _zz_337);
  assign _zz_49 = x_tmp_94[1 : 1];
  assign x_tmp_96 = _zz_49;
  assign all_zeros_96 = ((! (x_tmp_96 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_96 = ((! (x_tmp_96[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_42 = ((~ all_left_zeros_94) & _zz_341);
  assign non_zeros_lzc_42 = (left_all_zeros_count_46 + left_non_all_zeros_count_42);
  assign ret_94 = ((all_zeros_94 & whole_bit_count_48) + ((~ all_zeros_94) & non_zeros_lzc_42));
  assign left_non_all_zeros_count_43 = ((~ all_left_zeros_90) & _zz_342);
  assign non_zeros_lzc_43 = (left_all_zeros_count_45 + left_non_all_zeros_count_43);
  assign ret_90 = ((all_zeros_90 & whole_bit_count_46) + ((~ all_zeros_90) & non_zeros_lzc_43));
  assign left_non_all_zeros_count_44 = ((~ all_left_zeros_82) & _zz_343);
  assign non_zeros_lzc_44 = (left_all_zeros_count_43 + left_non_all_zeros_count_44);
  assign ret_82 = ((all_zeros_82 & whole_bit_count_42) + ((~ all_zeros_82) & non_zeros_lzc_44));
  assign left_non_all_zeros_count_45 = ((~ all_left_zeros_66) & _zz_344);
  assign non_zeros_lzc_45 = (left_all_zeros_count_39 + left_non_all_zeros_count_45);
  assign ret_66 = ((all_zeros_66 & whole_bit_count_34) + ((~ all_zeros_66) & non_zeros_lzc_45));
  assign left_all_zeros_count_47 = (all_left_zeros_65 & _zz_345);
  assign x_tmp_97 = x_tmp_65[31 : 16];
  assign all_zeros_97 = ((! (x_tmp_97 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_97 = ((! (x_tmp_97[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_49 = 5'h10;
  assign left_bit_count_49 = 5'h08;
  assign x_tmp_98 = x_tmp_97[7 : 0];
  assign all_zeros_98 = ((! (x_tmp_98 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_98 = ((! (x_tmp_98[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_50 = 4'b1000;
  assign left_bit_count_50 = 4'b0100;
  assign x_tmp_99 = x_tmp_98[3 : 0];
  assign all_zeros_99 = ((! (x_tmp_99 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_99 = ((! (x_tmp_99[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_51 = 3'b100;
  assign left_bit_count_51 = 3'b010;
  assign x_tmp_100 = x_tmp_99[1 : 0];
  assign all_zeros_100 = ((! (x_tmp_100 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_100 = ((! (x_tmp_100[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_52 = 2'b10;
  assign left_bit_count_52 = 2'b01;
  assign _zz_50 = x_tmp_100[0 : 0];
  assign x_tmp_101 = _zz_50;
  assign all_zeros_101 = ((! (x_tmp_101 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_101 = ((! (x_tmp_101[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_48 = (all_left_zeros_100 & _zz_347);
  assign _zz_51 = x_tmp_100[1 : 1];
  assign x_tmp_102 = _zz_51;
  assign all_zeros_102 = ((! (x_tmp_102 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_102 = ((! (x_tmp_102[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_46 = ((~ all_left_zeros_100) & _zz_351);
  assign non_zeros_lzc_46 = (left_all_zeros_count_48 + left_non_all_zeros_count_46);
  assign ret_100 = ((all_zeros_100 & whole_bit_count_52) + ((~ all_zeros_100) & non_zeros_lzc_46));
  assign left_all_zeros_count_49 = (all_left_zeros_99 & _zz_352);
  assign x_tmp_103 = x_tmp_99[3 : 2];
  assign all_zeros_103 = ((! (x_tmp_103 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_103 = ((! (x_tmp_103[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_53 = 2'b10;
  assign left_bit_count_53 = 2'b01;
  assign _zz_52 = x_tmp_103[0 : 0];
  assign x_tmp_104 = _zz_52;
  assign all_zeros_104 = ((! (x_tmp_104 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_104 = ((! (x_tmp_104[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_50 = (all_left_zeros_103 & _zz_354);
  assign _zz_53 = x_tmp_103[1 : 1];
  assign x_tmp_105 = _zz_53;
  assign all_zeros_105 = ((! (x_tmp_105 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_105 = ((! (x_tmp_105[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_47 = ((~ all_left_zeros_103) & _zz_358);
  assign non_zeros_lzc_47 = (left_all_zeros_count_50 + left_non_all_zeros_count_47);
  assign ret_103 = ((all_zeros_103 & whole_bit_count_53) + ((~ all_zeros_103) & non_zeros_lzc_47));
  assign left_non_all_zeros_count_48 = ((~ all_left_zeros_99) & _zz_359);
  assign non_zeros_lzc_48 = (left_all_zeros_count_49 + left_non_all_zeros_count_48);
  assign ret_99 = ((all_zeros_99 & whole_bit_count_51) + ((~ all_zeros_99) & non_zeros_lzc_48));
  assign left_all_zeros_count_51 = (all_left_zeros_98 & _zz_360);
  assign x_tmp_106 = x_tmp_98[7 : 4];
  assign all_zeros_106 = ((! (x_tmp_106 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_106 = ((! (x_tmp_106[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_54 = 3'b100;
  assign left_bit_count_54 = 3'b010;
  assign x_tmp_107 = x_tmp_106[1 : 0];
  assign all_zeros_107 = ((! (x_tmp_107 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_107 = ((! (x_tmp_107[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_55 = 2'b10;
  assign left_bit_count_55 = 2'b01;
  assign _zz_54 = x_tmp_107[0 : 0];
  assign x_tmp_108 = _zz_54;
  assign all_zeros_108 = ((! (x_tmp_108 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_108 = ((! (x_tmp_108[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_52 = (all_left_zeros_107 & _zz_362);
  assign _zz_55 = x_tmp_107[1 : 1];
  assign x_tmp_109 = _zz_55;
  assign all_zeros_109 = ((! (x_tmp_109 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_109 = ((! (x_tmp_109[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_49 = ((~ all_left_zeros_107) & _zz_366);
  assign non_zeros_lzc_49 = (left_all_zeros_count_52 + left_non_all_zeros_count_49);
  assign ret_107 = ((all_zeros_107 & whole_bit_count_55) + ((~ all_zeros_107) & non_zeros_lzc_49));
  assign left_all_zeros_count_53 = (all_left_zeros_106 & _zz_367);
  assign x_tmp_110 = x_tmp_106[3 : 2];
  assign all_zeros_110 = ((! (x_tmp_110 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_110 = ((! (x_tmp_110[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_56 = 2'b10;
  assign left_bit_count_56 = 2'b01;
  assign _zz_56 = x_tmp_110[0 : 0];
  assign x_tmp_111 = _zz_56;
  assign all_zeros_111 = ((! (x_tmp_111 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_111 = ((! (x_tmp_111[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_54 = (all_left_zeros_110 & _zz_369);
  assign _zz_57 = x_tmp_110[1 : 1];
  assign x_tmp_112 = _zz_57;
  assign all_zeros_112 = ((! (x_tmp_112 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_112 = ((! (x_tmp_112[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_50 = ((~ all_left_zeros_110) & _zz_373);
  assign non_zeros_lzc_50 = (left_all_zeros_count_54 + left_non_all_zeros_count_50);
  assign ret_110 = ((all_zeros_110 & whole_bit_count_56) + ((~ all_zeros_110) & non_zeros_lzc_50));
  assign left_non_all_zeros_count_51 = ((~ all_left_zeros_106) & _zz_374);
  assign non_zeros_lzc_51 = (left_all_zeros_count_53 + left_non_all_zeros_count_51);
  assign ret_106 = ((all_zeros_106 & whole_bit_count_54) + ((~ all_zeros_106) & non_zeros_lzc_51));
  assign left_non_all_zeros_count_52 = ((~ all_left_zeros_98) & _zz_375);
  assign non_zeros_lzc_52 = (left_all_zeros_count_51 + left_non_all_zeros_count_52);
  assign ret_98 = ((all_zeros_98 & whole_bit_count_50) + ((~ all_zeros_98) & non_zeros_lzc_52));
  assign left_all_zeros_count_55 = (all_left_zeros_97 & _zz_376);
  assign x_tmp_113 = x_tmp_97[15 : 8];
  assign all_zeros_113 = ((! (x_tmp_113 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_113 = ((! (x_tmp_113[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_57 = 4'b1000;
  assign left_bit_count_57 = 4'b0100;
  assign x_tmp_114 = x_tmp_113[3 : 0];
  assign all_zeros_114 = ((! (x_tmp_114 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_114 = ((! (x_tmp_114[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_58 = 3'b100;
  assign left_bit_count_58 = 3'b010;
  assign x_tmp_115 = x_tmp_114[1 : 0];
  assign all_zeros_115 = ((! (x_tmp_115 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_115 = ((! (x_tmp_115[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_59 = 2'b10;
  assign left_bit_count_59 = 2'b01;
  assign _zz_58 = x_tmp_115[0 : 0];
  assign x_tmp_116 = _zz_58;
  assign all_zeros_116 = ((! (x_tmp_116 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_116 = ((! (x_tmp_116[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_56 = (all_left_zeros_115 & _zz_378);
  assign _zz_59 = x_tmp_115[1 : 1];
  assign x_tmp_117 = _zz_59;
  assign all_zeros_117 = ((! (x_tmp_117 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_117 = ((! (x_tmp_117[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_53 = ((~ all_left_zeros_115) & _zz_382);
  assign non_zeros_lzc_53 = (left_all_zeros_count_56 + left_non_all_zeros_count_53);
  assign ret_115 = ((all_zeros_115 & whole_bit_count_59) + ((~ all_zeros_115) & non_zeros_lzc_53));
  assign left_all_zeros_count_57 = (all_left_zeros_114 & _zz_383);
  assign x_tmp_118 = x_tmp_114[3 : 2];
  assign all_zeros_118 = ((! (x_tmp_118 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_118 = ((! (x_tmp_118[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_60 = 2'b10;
  assign left_bit_count_60 = 2'b01;
  assign _zz_60 = x_tmp_118[0 : 0];
  assign x_tmp_119 = _zz_60;
  assign all_zeros_119 = ((! (x_tmp_119 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_119 = ((! (x_tmp_119[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_58 = (all_left_zeros_118 & _zz_385);
  assign _zz_61 = x_tmp_118[1 : 1];
  assign x_tmp_120 = _zz_61;
  assign all_zeros_120 = ((! (x_tmp_120 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_120 = ((! (x_tmp_120[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_54 = ((~ all_left_zeros_118) & _zz_389);
  assign non_zeros_lzc_54 = (left_all_zeros_count_58 + left_non_all_zeros_count_54);
  assign ret_118 = ((all_zeros_118 & whole_bit_count_60) + ((~ all_zeros_118) & non_zeros_lzc_54));
  assign left_non_all_zeros_count_55 = ((~ all_left_zeros_114) & _zz_390);
  assign non_zeros_lzc_55 = (left_all_zeros_count_57 + left_non_all_zeros_count_55);
  assign ret_114 = ((all_zeros_114 & whole_bit_count_58) + ((~ all_zeros_114) & non_zeros_lzc_55));
  assign left_all_zeros_count_59 = (all_left_zeros_113 & _zz_391);
  assign x_tmp_121 = x_tmp_113[7 : 4];
  assign all_zeros_121 = ((! (x_tmp_121 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_121 = ((! (x_tmp_121[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_61 = 3'b100;
  assign left_bit_count_61 = 3'b010;
  assign x_tmp_122 = x_tmp_121[1 : 0];
  assign all_zeros_122 = ((! (x_tmp_122 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_122 = ((! (x_tmp_122[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_62 = 2'b10;
  assign left_bit_count_62 = 2'b01;
  assign _zz_62 = x_tmp_122[0 : 0];
  assign x_tmp_123 = _zz_62;
  assign all_zeros_123 = ((! (x_tmp_123 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_123 = ((! (x_tmp_123[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_60 = (all_left_zeros_122 & _zz_393);
  assign _zz_63 = x_tmp_122[1 : 1];
  assign x_tmp_124 = _zz_63;
  assign all_zeros_124 = ((! (x_tmp_124 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_124 = ((! (x_tmp_124[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_56 = ((~ all_left_zeros_122) & _zz_397);
  assign non_zeros_lzc_56 = (left_all_zeros_count_60 + left_non_all_zeros_count_56);
  assign ret_122 = ((all_zeros_122 & whole_bit_count_62) + ((~ all_zeros_122) & non_zeros_lzc_56));
  assign left_all_zeros_count_61 = (all_left_zeros_121 & _zz_398);
  assign x_tmp_125 = x_tmp_121[3 : 2];
  assign all_zeros_125 = ((! (x_tmp_125 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_125 = ((! (x_tmp_125[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_63 = 2'b10;
  assign left_bit_count_63 = 2'b01;
  assign _zz_64 = x_tmp_125[0 : 0];
  assign x_tmp_126 = _zz_64;
  assign all_zeros_126 = ((! (x_tmp_126 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_126 = ((! (x_tmp_126[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_62 = (all_left_zeros_125 & _zz_400);
  assign _zz_65 = x_tmp_125[1 : 1];
  assign x_tmp_127 = _zz_65;
  assign all_zeros_127 = ((! (x_tmp_127 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_127 = ((! (x_tmp_127[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_57 = ((~ all_left_zeros_125) & _zz_404);
  assign non_zeros_lzc_57 = (left_all_zeros_count_62 + left_non_all_zeros_count_57);
  assign ret_125 = ((all_zeros_125 & whole_bit_count_63) + ((~ all_zeros_125) & non_zeros_lzc_57));
  assign left_non_all_zeros_count_58 = ((~ all_left_zeros_121) & _zz_405);
  assign non_zeros_lzc_58 = (left_all_zeros_count_61 + left_non_all_zeros_count_58);
  assign ret_121 = ((all_zeros_121 & whole_bit_count_61) + ((~ all_zeros_121) & non_zeros_lzc_58));
  assign left_non_all_zeros_count_59 = ((~ all_left_zeros_113) & _zz_406);
  assign non_zeros_lzc_59 = (left_all_zeros_count_59 + left_non_all_zeros_count_59);
  assign ret_113 = ((all_zeros_113 & whole_bit_count_57) + ((~ all_zeros_113) & non_zeros_lzc_59));
  assign left_non_all_zeros_count_60 = ((~ all_left_zeros_97) & _zz_407);
  assign non_zeros_lzc_60 = (left_all_zeros_count_55 + left_non_all_zeros_count_60);
  assign ret_97 = ((all_zeros_97 & whole_bit_count_49) + ((~ all_zeros_97) & non_zeros_lzc_60));
  assign left_non_all_zeros_count_61 = ((~ all_left_zeros_65) & _zz_408);
  assign non_zeros_lzc_61 = (left_all_zeros_count_47 + left_non_all_zeros_count_61);
  assign ret_65 = ((all_zeros_65 & whole_bit_count_33) + ((~ all_zeros_65) & non_zeros_lzc_61));
  assign left_non_all_zeros_count_62 = ((~ all_left_zeros_1) & _zz_409);
  assign non_zeros_lzc_62 = (left_all_zeros_count_31 + left_non_all_zeros_count_62);
  assign ret_1 = ((all_zeros_1 & whole_bit_count_1) + ((~ all_zeros_1) & non_zeros_lzc_62));
  assign left_all_zeros_count_63 = (all_left_zeros & _zz_410);
  assign x_tmp_128 = x_tmp[127 : 64];
  assign all_zeros_128 = ((! (x_tmp_128 != 64'h0)) ? 7'h7f : 7'h0);
  assign all_left_zeros_128 = ((! (x_tmp_128[63 : 32] != 32'h0)) ? 7'h7f : 7'h0);
  assign whole_bit_count_64 = 7'h40;
  assign left_bit_count_64 = 7'h20;
  assign x_tmp_129 = x_tmp_128[31 : 0];
  assign all_zeros_129 = ((! (x_tmp_129 != 32'h0)) ? 6'h3f : 6'h0);
  assign all_left_zeros_129 = ((! (x_tmp_129[31 : 16] != 16'h0)) ? 6'h3f : 6'h0);
  assign whole_bit_count_65 = 6'h20;
  assign left_bit_count_65 = 6'h10;
  assign x_tmp_130 = x_tmp_129[15 : 0];
  assign all_zeros_130 = ((! (x_tmp_130 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_130 = ((! (x_tmp_130[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_66 = 5'h10;
  assign left_bit_count_66 = 5'h08;
  assign x_tmp_131 = x_tmp_130[7 : 0];
  assign all_zeros_131 = ((! (x_tmp_131 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_131 = ((! (x_tmp_131[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_67 = 4'b1000;
  assign left_bit_count_67 = 4'b0100;
  assign x_tmp_132 = x_tmp_131[3 : 0];
  assign all_zeros_132 = ((! (x_tmp_132 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_132 = ((! (x_tmp_132[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_68 = 3'b100;
  assign left_bit_count_68 = 3'b010;
  assign x_tmp_133 = x_tmp_132[1 : 0];
  assign all_zeros_133 = ((! (x_tmp_133 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_133 = ((! (x_tmp_133[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_69 = 2'b10;
  assign left_bit_count_69 = 2'b01;
  assign _zz_66 = x_tmp_133[0 : 0];
  assign x_tmp_134 = _zz_66;
  assign all_zeros_134 = ((! (x_tmp_134 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_134 = ((! (x_tmp_134[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_64 = (all_left_zeros_133 & _zz_412);
  assign _zz_67 = x_tmp_133[1 : 1];
  assign x_tmp_135 = _zz_67;
  assign all_zeros_135 = ((! (x_tmp_135 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_135 = ((! (x_tmp_135[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_63 = ((~ all_left_zeros_133) & _zz_416);
  assign non_zeros_lzc_63 = (left_all_zeros_count_64 + left_non_all_zeros_count_63);
  assign ret_133 = ((all_zeros_133 & whole_bit_count_69) + ((~ all_zeros_133) & non_zeros_lzc_63));
  assign left_all_zeros_count_65 = (all_left_zeros_132 & _zz_417);
  assign x_tmp_136 = x_tmp_132[3 : 2];
  assign all_zeros_136 = ((! (x_tmp_136 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_136 = ((! (x_tmp_136[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_70 = 2'b10;
  assign left_bit_count_70 = 2'b01;
  assign _zz_68 = x_tmp_136[0 : 0];
  assign x_tmp_137 = _zz_68;
  assign all_zeros_137 = ((! (x_tmp_137 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_137 = ((! (x_tmp_137[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_66 = (all_left_zeros_136 & _zz_419);
  assign _zz_69 = x_tmp_136[1 : 1];
  assign x_tmp_138 = _zz_69;
  assign all_zeros_138 = ((! (x_tmp_138 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_138 = ((! (x_tmp_138[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_64 = ((~ all_left_zeros_136) & _zz_423);
  assign non_zeros_lzc_64 = (left_all_zeros_count_66 + left_non_all_zeros_count_64);
  assign ret_136 = ((all_zeros_136 & whole_bit_count_70) + ((~ all_zeros_136) & non_zeros_lzc_64));
  assign left_non_all_zeros_count_65 = ((~ all_left_zeros_132) & _zz_424);
  assign non_zeros_lzc_65 = (left_all_zeros_count_65 + left_non_all_zeros_count_65);
  assign ret_132 = ((all_zeros_132 & whole_bit_count_68) + ((~ all_zeros_132) & non_zeros_lzc_65));
  assign left_all_zeros_count_67 = (all_left_zeros_131 & _zz_425);
  assign x_tmp_139 = x_tmp_131[7 : 4];
  assign all_zeros_139 = ((! (x_tmp_139 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_139 = ((! (x_tmp_139[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_71 = 3'b100;
  assign left_bit_count_71 = 3'b010;
  assign x_tmp_140 = x_tmp_139[1 : 0];
  assign all_zeros_140 = ((! (x_tmp_140 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_140 = ((! (x_tmp_140[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_72 = 2'b10;
  assign left_bit_count_72 = 2'b01;
  assign _zz_70 = x_tmp_140[0 : 0];
  assign x_tmp_141 = _zz_70;
  assign all_zeros_141 = ((! (x_tmp_141 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_141 = ((! (x_tmp_141[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_68 = (all_left_zeros_140 & _zz_427);
  assign _zz_71 = x_tmp_140[1 : 1];
  assign x_tmp_142 = _zz_71;
  assign all_zeros_142 = ((! (x_tmp_142 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_142 = ((! (x_tmp_142[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_66 = ((~ all_left_zeros_140) & _zz_431);
  assign non_zeros_lzc_66 = (left_all_zeros_count_68 + left_non_all_zeros_count_66);
  assign ret_140 = ((all_zeros_140 & whole_bit_count_72) + ((~ all_zeros_140) & non_zeros_lzc_66));
  assign left_all_zeros_count_69 = (all_left_zeros_139 & _zz_432);
  assign x_tmp_143 = x_tmp_139[3 : 2];
  assign all_zeros_143 = ((! (x_tmp_143 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_143 = ((! (x_tmp_143[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_73 = 2'b10;
  assign left_bit_count_73 = 2'b01;
  assign _zz_72 = x_tmp_143[0 : 0];
  assign x_tmp_144 = _zz_72;
  assign all_zeros_144 = ((! (x_tmp_144 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_144 = ((! (x_tmp_144[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_70 = (all_left_zeros_143 & _zz_434);
  assign _zz_73 = x_tmp_143[1 : 1];
  assign x_tmp_145 = _zz_73;
  assign all_zeros_145 = ((! (x_tmp_145 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_145 = ((! (x_tmp_145[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_67 = ((~ all_left_zeros_143) & _zz_438);
  assign non_zeros_lzc_67 = (left_all_zeros_count_70 + left_non_all_zeros_count_67);
  assign ret_143 = ((all_zeros_143 & whole_bit_count_73) + ((~ all_zeros_143) & non_zeros_lzc_67));
  assign left_non_all_zeros_count_68 = ((~ all_left_zeros_139) & _zz_439);
  assign non_zeros_lzc_68 = (left_all_zeros_count_69 + left_non_all_zeros_count_68);
  assign ret_139 = ((all_zeros_139 & whole_bit_count_71) + ((~ all_zeros_139) & non_zeros_lzc_68));
  assign left_non_all_zeros_count_69 = ((~ all_left_zeros_131) & _zz_440);
  assign non_zeros_lzc_69 = (left_all_zeros_count_67 + left_non_all_zeros_count_69);
  assign ret_131 = ((all_zeros_131 & whole_bit_count_67) + ((~ all_zeros_131) & non_zeros_lzc_69));
  assign left_all_zeros_count_71 = (all_left_zeros_130 & _zz_441);
  assign x_tmp_146 = x_tmp_130[15 : 8];
  assign all_zeros_146 = ((! (x_tmp_146 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_146 = ((! (x_tmp_146[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_74 = 4'b1000;
  assign left_bit_count_74 = 4'b0100;
  assign x_tmp_147 = x_tmp_146[3 : 0];
  assign all_zeros_147 = ((! (x_tmp_147 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_147 = ((! (x_tmp_147[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_75 = 3'b100;
  assign left_bit_count_75 = 3'b010;
  assign x_tmp_148 = x_tmp_147[1 : 0];
  assign all_zeros_148 = ((! (x_tmp_148 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_148 = ((! (x_tmp_148[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_76 = 2'b10;
  assign left_bit_count_76 = 2'b01;
  assign _zz_74 = x_tmp_148[0 : 0];
  assign x_tmp_149 = _zz_74;
  assign all_zeros_149 = ((! (x_tmp_149 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_149 = ((! (x_tmp_149[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_72 = (all_left_zeros_148 & _zz_443);
  assign _zz_75 = x_tmp_148[1 : 1];
  assign x_tmp_150 = _zz_75;
  assign all_zeros_150 = ((! (x_tmp_150 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_150 = ((! (x_tmp_150[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_70 = ((~ all_left_zeros_148) & _zz_447);
  assign non_zeros_lzc_70 = (left_all_zeros_count_72 + left_non_all_zeros_count_70);
  assign ret_148 = ((all_zeros_148 & whole_bit_count_76) + ((~ all_zeros_148) & non_zeros_lzc_70));
  assign left_all_zeros_count_73 = (all_left_zeros_147 & _zz_448);
  assign x_tmp_151 = x_tmp_147[3 : 2];
  assign all_zeros_151 = ((! (x_tmp_151 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_151 = ((! (x_tmp_151[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_77 = 2'b10;
  assign left_bit_count_77 = 2'b01;
  assign _zz_76 = x_tmp_151[0 : 0];
  assign x_tmp_152 = _zz_76;
  assign all_zeros_152 = ((! (x_tmp_152 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_152 = ((! (x_tmp_152[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_74 = (all_left_zeros_151 & _zz_450);
  assign _zz_77 = x_tmp_151[1 : 1];
  assign x_tmp_153 = _zz_77;
  assign all_zeros_153 = ((! (x_tmp_153 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_153 = ((! (x_tmp_153[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_71 = ((~ all_left_zeros_151) & _zz_454);
  assign non_zeros_lzc_71 = (left_all_zeros_count_74 + left_non_all_zeros_count_71);
  assign ret_151 = ((all_zeros_151 & whole_bit_count_77) + ((~ all_zeros_151) & non_zeros_lzc_71));
  assign left_non_all_zeros_count_72 = ((~ all_left_zeros_147) & _zz_455);
  assign non_zeros_lzc_72 = (left_all_zeros_count_73 + left_non_all_zeros_count_72);
  assign ret_147 = ((all_zeros_147 & whole_bit_count_75) + ((~ all_zeros_147) & non_zeros_lzc_72));
  assign left_all_zeros_count_75 = (all_left_zeros_146 & _zz_456);
  assign x_tmp_154 = x_tmp_146[7 : 4];
  assign all_zeros_154 = ((! (x_tmp_154 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_154 = ((! (x_tmp_154[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_78 = 3'b100;
  assign left_bit_count_78 = 3'b010;
  assign x_tmp_155 = x_tmp_154[1 : 0];
  assign all_zeros_155 = ((! (x_tmp_155 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_155 = ((! (x_tmp_155[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_79 = 2'b10;
  assign left_bit_count_79 = 2'b01;
  assign _zz_78 = x_tmp_155[0 : 0];
  assign x_tmp_156 = _zz_78;
  assign all_zeros_156 = ((! (x_tmp_156 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_156 = ((! (x_tmp_156[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_76 = (all_left_zeros_155 & _zz_458);
  assign _zz_79 = x_tmp_155[1 : 1];
  assign x_tmp_157 = _zz_79;
  assign all_zeros_157 = ((! (x_tmp_157 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_157 = ((! (x_tmp_157[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_73 = ((~ all_left_zeros_155) & _zz_462);
  assign non_zeros_lzc_73 = (left_all_zeros_count_76 + left_non_all_zeros_count_73);
  assign ret_155 = ((all_zeros_155 & whole_bit_count_79) + ((~ all_zeros_155) & non_zeros_lzc_73));
  assign left_all_zeros_count_77 = (all_left_zeros_154 & _zz_463);
  assign x_tmp_158 = x_tmp_154[3 : 2];
  assign all_zeros_158 = ((! (x_tmp_158 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_158 = ((! (x_tmp_158[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_80 = 2'b10;
  assign left_bit_count_80 = 2'b01;
  assign _zz_80 = x_tmp_158[0 : 0];
  assign x_tmp_159 = _zz_80;
  assign all_zeros_159 = ((! (x_tmp_159 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_159 = ((! (x_tmp_159[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_78 = (all_left_zeros_158 & _zz_465);
  assign _zz_81 = x_tmp_158[1 : 1];
  assign x_tmp_160 = _zz_81;
  assign all_zeros_160 = ((! (x_tmp_160 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_160 = ((! (x_tmp_160[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_74 = ((~ all_left_zeros_158) & _zz_469);
  assign non_zeros_lzc_74 = (left_all_zeros_count_78 + left_non_all_zeros_count_74);
  assign ret_158 = ((all_zeros_158 & whole_bit_count_80) + ((~ all_zeros_158) & non_zeros_lzc_74));
  assign left_non_all_zeros_count_75 = ((~ all_left_zeros_154) & _zz_470);
  assign non_zeros_lzc_75 = (left_all_zeros_count_77 + left_non_all_zeros_count_75);
  assign ret_154 = ((all_zeros_154 & whole_bit_count_78) + ((~ all_zeros_154) & non_zeros_lzc_75));
  assign left_non_all_zeros_count_76 = ((~ all_left_zeros_146) & _zz_471);
  assign non_zeros_lzc_76 = (left_all_zeros_count_75 + left_non_all_zeros_count_76);
  assign ret_146 = ((all_zeros_146 & whole_bit_count_74) + ((~ all_zeros_146) & non_zeros_lzc_76));
  assign left_non_all_zeros_count_77 = ((~ all_left_zeros_130) & _zz_472);
  assign non_zeros_lzc_77 = (left_all_zeros_count_71 + left_non_all_zeros_count_77);
  assign ret_130 = ((all_zeros_130 & whole_bit_count_66) + ((~ all_zeros_130) & non_zeros_lzc_77));
  assign left_all_zeros_count_79 = (all_left_zeros_129 & _zz_473);
  assign x_tmp_161 = x_tmp_129[31 : 16];
  assign all_zeros_161 = ((! (x_tmp_161 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_161 = ((! (x_tmp_161[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_81 = 5'h10;
  assign left_bit_count_81 = 5'h08;
  assign x_tmp_162 = x_tmp_161[7 : 0];
  assign all_zeros_162 = ((! (x_tmp_162 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_162 = ((! (x_tmp_162[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_82 = 4'b1000;
  assign left_bit_count_82 = 4'b0100;
  assign x_tmp_163 = x_tmp_162[3 : 0];
  assign all_zeros_163 = ((! (x_tmp_163 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_163 = ((! (x_tmp_163[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_83 = 3'b100;
  assign left_bit_count_83 = 3'b010;
  assign x_tmp_164 = x_tmp_163[1 : 0];
  assign all_zeros_164 = ((! (x_tmp_164 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_164 = ((! (x_tmp_164[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_84 = 2'b10;
  assign left_bit_count_84 = 2'b01;
  assign _zz_82 = x_tmp_164[0 : 0];
  assign x_tmp_165 = _zz_82;
  assign all_zeros_165 = ((! (x_tmp_165 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_165 = ((! (x_tmp_165[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_80 = (all_left_zeros_164 & _zz_475);
  assign _zz_83 = x_tmp_164[1 : 1];
  assign x_tmp_166 = _zz_83;
  assign all_zeros_166 = ((! (x_tmp_166 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_166 = ((! (x_tmp_166[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_78 = ((~ all_left_zeros_164) & _zz_479);
  assign non_zeros_lzc_78 = (left_all_zeros_count_80 + left_non_all_zeros_count_78);
  assign ret_164 = ((all_zeros_164 & whole_bit_count_84) + ((~ all_zeros_164) & non_zeros_lzc_78));
  assign left_all_zeros_count_81 = (all_left_zeros_163 & _zz_480);
  assign x_tmp_167 = x_tmp_163[3 : 2];
  assign all_zeros_167 = ((! (x_tmp_167 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_167 = ((! (x_tmp_167[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_85 = 2'b10;
  assign left_bit_count_85 = 2'b01;
  assign _zz_84 = x_tmp_167[0 : 0];
  assign x_tmp_168 = _zz_84;
  assign all_zeros_168 = ((! (x_tmp_168 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_168 = ((! (x_tmp_168[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_82 = (all_left_zeros_167 & _zz_482);
  assign _zz_85 = x_tmp_167[1 : 1];
  assign x_tmp_169 = _zz_85;
  assign all_zeros_169 = ((! (x_tmp_169 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_169 = ((! (x_tmp_169[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_79 = ((~ all_left_zeros_167) & _zz_486);
  assign non_zeros_lzc_79 = (left_all_zeros_count_82 + left_non_all_zeros_count_79);
  assign ret_167 = ((all_zeros_167 & whole_bit_count_85) + ((~ all_zeros_167) & non_zeros_lzc_79));
  assign left_non_all_zeros_count_80 = ((~ all_left_zeros_163) & _zz_487);
  assign non_zeros_lzc_80 = (left_all_zeros_count_81 + left_non_all_zeros_count_80);
  assign ret_163 = ((all_zeros_163 & whole_bit_count_83) + ((~ all_zeros_163) & non_zeros_lzc_80));
  assign left_all_zeros_count_83 = (all_left_zeros_162 & _zz_488);
  assign x_tmp_170 = x_tmp_162[7 : 4];
  assign all_zeros_170 = ((! (x_tmp_170 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_170 = ((! (x_tmp_170[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_86 = 3'b100;
  assign left_bit_count_86 = 3'b010;
  assign x_tmp_171 = x_tmp_170[1 : 0];
  assign all_zeros_171 = ((! (x_tmp_171 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_171 = ((! (x_tmp_171[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_87 = 2'b10;
  assign left_bit_count_87 = 2'b01;
  assign _zz_86 = x_tmp_171[0 : 0];
  assign x_tmp_172 = _zz_86;
  assign all_zeros_172 = ((! (x_tmp_172 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_172 = ((! (x_tmp_172[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_84 = (all_left_zeros_171 & _zz_490);
  assign _zz_87 = x_tmp_171[1 : 1];
  assign x_tmp_173 = _zz_87;
  assign all_zeros_173 = ((! (x_tmp_173 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_173 = ((! (x_tmp_173[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_81 = ((~ all_left_zeros_171) & _zz_494);
  assign non_zeros_lzc_81 = (left_all_zeros_count_84 + left_non_all_zeros_count_81);
  assign ret_171 = ((all_zeros_171 & whole_bit_count_87) + ((~ all_zeros_171) & non_zeros_lzc_81));
  assign left_all_zeros_count_85 = (all_left_zeros_170 & _zz_495);
  assign x_tmp_174 = x_tmp_170[3 : 2];
  assign all_zeros_174 = ((! (x_tmp_174 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_174 = ((! (x_tmp_174[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_88 = 2'b10;
  assign left_bit_count_88 = 2'b01;
  assign _zz_88 = x_tmp_174[0 : 0];
  assign x_tmp_175 = _zz_88;
  assign all_zeros_175 = ((! (x_tmp_175 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_175 = ((! (x_tmp_175[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_86 = (all_left_zeros_174 & _zz_497);
  assign _zz_89 = x_tmp_174[1 : 1];
  assign x_tmp_176 = _zz_89;
  assign all_zeros_176 = ((! (x_tmp_176 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_176 = ((! (x_tmp_176[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_82 = ((~ all_left_zeros_174) & _zz_501);
  assign non_zeros_lzc_82 = (left_all_zeros_count_86 + left_non_all_zeros_count_82);
  assign ret_174 = ((all_zeros_174 & whole_bit_count_88) + ((~ all_zeros_174) & non_zeros_lzc_82));
  assign left_non_all_zeros_count_83 = ((~ all_left_zeros_170) & _zz_502);
  assign non_zeros_lzc_83 = (left_all_zeros_count_85 + left_non_all_zeros_count_83);
  assign ret_170 = ((all_zeros_170 & whole_bit_count_86) + ((~ all_zeros_170) & non_zeros_lzc_83));
  assign left_non_all_zeros_count_84 = ((~ all_left_zeros_162) & _zz_503);
  assign non_zeros_lzc_84 = (left_all_zeros_count_83 + left_non_all_zeros_count_84);
  assign ret_162 = ((all_zeros_162 & whole_bit_count_82) + ((~ all_zeros_162) & non_zeros_lzc_84));
  assign left_all_zeros_count_87 = (all_left_zeros_161 & _zz_504);
  assign x_tmp_177 = x_tmp_161[15 : 8];
  assign all_zeros_177 = ((! (x_tmp_177 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_177 = ((! (x_tmp_177[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_89 = 4'b1000;
  assign left_bit_count_89 = 4'b0100;
  assign x_tmp_178 = x_tmp_177[3 : 0];
  assign all_zeros_178 = ((! (x_tmp_178 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_178 = ((! (x_tmp_178[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_90 = 3'b100;
  assign left_bit_count_90 = 3'b010;
  assign x_tmp_179 = x_tmp_178[1 : 0];
  assign all_zeros_179 = ((! (x_tmp_179 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_179 = ((! (x_tmp_179[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_91 = 2'b10;
  assign left_bit_count_91 = 2'b01;
  assign _zz_90 = x_tmp_179[0 : 0];
  assign x_tmp_180 = _zz_90;
  assign all_zeros_180 = ((! (x_tmp_180 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_180 = ((! (x_tmp_180[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_88 = (all_left_zeros_179 & _zz_506);
  assign _zz_91 = x_tmp_179[1 : 1];
  assign x_tmp_181 = _zz_91;
  assign all_zeros_181 = ((! (x_tmp_181 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_181 = ((! (x_tmp_181[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_85 = ((~ all_left_zeros_179) & _zz_510);
  assign non_zeros_lzc_85 = (left_all_zeros_count_88 + left_non_all_zeros_count_85);
  assign ret_179 = ((all_zeros_179 & whole_bit_count_91) + ((~ all_zeros_179) & non_zeros_lzc_85));
  assign left_all_zeros_count_89 = (all_left_zeros_178 & _zz_511);
  assign x_tmp_182 = x_tmp_178[3 : 2];
  assign all_zeros_182 = ((! (x_tmp_182 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_182 = ((! (x_tmp_182[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_92 = 2'b10;
  assign left_bit_count_92 = 2'b01;
  assign _zz_92 = x_tmp_182[0 : 0];
  assign x_tmp_183 = _zz_92;
  assign all_zeros_183 = ((! (x_tmp_183 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_183 = ((! (x_tmp_183[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_90 = (all_left_zeros_182 & _zz_513);
  assign _zz_93 = x_tmp_182[1 : 1];
  assign x_tmp_184 = _zz_93;
  assign all_zeros_184 = ((! (x_tmp_184 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_184 = ((! (x_tmp_184[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_86 = ((~ all_left_zeros_182) & _zz_517);
  assign non_zeros_lzc_86 = (left_all_zeros_count_90 + left_non_all_zeros_count_86);
  assign ret_182 = ((all_zeros_182 & whole_bit_count_92) + ((~ all_zeros_182) & non_zeros_lzc_86));
  assign left_non_all_zeros_count_87 = ((~ all_left_zeros_178) & _zz_518);
  assign non_zeros_lzc_87 = (left_all_zeros_count_89 + left_non_all_zeros_count_87);
  assign ret_178 = ((all_zeros_178 & whole_bit_count_90) + ((~ all_zeros_178) & non_zeros_lzc_87));
  assign left_all_zeros_count_91 = (all_left_zeros_177 & _zz_519);
  assign x_tmp_185 = x_tmp_177[7 : 4];
  assign all_zeros_185 = ((! (x_tmp_185 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_185 = ((! (x_tmp_185[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_93 = 3'b100;
  assign left_bit_count_93 = 3'b010;
  assign x_tmp_186 = x_tmp_185[1 : 0];
  assign all_zeros_186 = ((! (x_tmp_186 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_186 = ((! (x_tmp_186[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_94 = 2'b10;
  assign left_bit_count_94 = 2'b01;
  assign _zz_94 = x_tmp_186[0 : 0];
  assign x_tmp_187 = _zz_94;
  assign all_zeros_187 = ((! (x_tmp_187 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_187 = ((! (x_tmp_187[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_92 = (all_left_zeros_186 & _zz_521);
  assign _zz_95 = x_tmp_186[1 : 1];
  assign x_tmp_188 = _zz_95;
  assign all_zeros_188 = ((! (x_tmp_188 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_188 = ((! (x_tmp_188[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_88 = ((~ all_left_zeros_186) & _zz_525);
  assign non_zeros_lzc_88 = (left_all_zeros_count_92 + left_non_all_zeros_count_88);
  assign ret_186 = ((all_zeros_186 & whole_bit_count_94) + ((~ all_zeros_186) & non_zeros_lzc_88));
  assign left_all_zeros_count_93 = (all_left_zeros_185 & _zz_526);
  assign x_tmp_189 = x_tmp_185[3 : 2];
  assign all_zeros_189 = ((! (x_tmp_189 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_189 = ((! (x_tmp_189[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_95 = 2'b10;
  assign left_bit_count_95 = 2'b01;
  assign _zz_96 = x_tmp_189[0 : 0];
  assign x_tmp_190 = _zz_96;
  assign all_zeros_190 = ((! (x_tmp_190 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_190 = ((! (x_tmp_190[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_94 = (all_left_zeros_189 & _zz_528);
  assign _zz_97 = x_tmp_189[1 : 1];
  assign x_tmp_191 = _zz_97;
  assign all_zeros_191 = ((! (x_tmp_191 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_191 = ((! (x_tmp_191[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_89 = ((~ all_left_zeros_189) & _zz_532);
  assign non_zeros_lzc_89 = (left_all_zeros_count_94 + left_non_all_zeros_count_89);
  assign ret_189 = ((all_zeros_189 & whole_bit_count_95) + ((~ all_zeros_189) & non_zeros_lzc_89));
  assign left_non_all_zeros_count_90 = ((~ all_left_zeros_185) & _zz_533);
  assign non_zeros_lzc_90 = (left_all_zeros_count_93 + left_non_all_zeros_count_90);
  assign ret_185 = ((all_zeros_185 & whole_bit_count_93) + ((~ all_zeros_185) & non_zeros_lzc_90));
  assign left_non_all_zeros_count_91 = ((~ all_left_zeros_177) & _zz_534);
  assign non_zeros_lzc_91 = (left_all_zeros_count_91 + left_non_all_zeros_count_91);
  assign ret_177 = ((all_zeros_177 & whole_bit_count_89) + ((~ all_zeros_177) & non_zeros_lzc_91));
  assign left_non_all_zeros_count_92 = ((~ all_left_zeros_161) & _zz_535);
  assign non_zeros_lzc_92 = (left_all_zeros_count_87 + left_non_all_zeros_count_92);
  assign ret_161 = ((all_zeros_161 & whole_bit_count_81) + ((~ all_zeros_161) & non_zeros_lzc_92));
  assign left_non_all_zeros_count_93 = ((~ all_left_zeros_129) & _zz_536);
  assign non_zeros_lzc_93 = (left_all_zeros_count_79 + left_non_all_zeros_count_93);
  assign ret_129 = ((all_zeros_129 & whole_bit_count_65) + ((~ all_zeros_129) & non_zeros_lzc_93));
  assign left_all_zeros_count_95 = (all_left_zeros_128 & _zz_537);
  assign x_tmp_192 = x_tmp_128[63 : 32];
  assign all_zeros_192 = ((! (x_tmp_192 != 32'h0)) ? 6'h3f : 6'h0);
  assign all_left_zeros_192 = ((! (x_tmp_192[31 : 16] != 16'h0)) ? 6'h3f : 6'h0);
  assign whole_bit_count_96 = 6'h20;
  assign left_bit_count_96 = 6'h10;
  assign x_tmp_193 = x_tmp_192[15 : 0];
  assign all_zeros_193 = ((! (x_tmp_193 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_193 = ((! (x_tmp_193[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_97 = 5'h10;
  assign left_bit_count_97 = 5'h08;
  assign x_tmp_194 = x_tmp_193[7 : 0];
  assign all_zeros_194 = ((! (x_tmp_194 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_194 = ((! (x_tmp_194[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_98 = 4'b1000;
  assign left_bit_count_98 = 4'b0100;
  assign x_tmp_195 = x_tmp_194[3 : 0];
  assign all_zeros_195 = ((! (x_tmp_195 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_195 = ((! (x_tmp_195[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_99 = 3'b100;
  assign left_bit_count_99 = 3'b010;
  assign x_tmp_196 = x_tmp_195[1 : 0];
  assign all_zeros_196 = ((! (x_tmp_196 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_196 = ((! (x_tmp_196[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_100 = 2'b10;
  assign left_bit_count_100 = 2'b01;
  assign _zz_98 = x_tmp_196[0 : 0];
  assign x_tmp_197 = _zz_98;
  assign all_zeros_197 = ((! (x_tmp_197 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_197 = ((! (x_tmp_197[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_96 = (all_left_zeros_196 & _zz_539);
  assign _zz_99 = x_tmp_196[1 : 1];
  assign x_tmp_198 = _zz_99;
  assign all_zeros_198 = ((! (x_tmp_198 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_198 = ((! (x_tmp_198[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_94 = ((~ all_left_zeros_196) & _zz_543);
  assign non_zeros_lzc_94 = (left_all_zeros_count_96 + left_non_all_zeros_count_94);
  assign ret_196 = ((all_zeros_196 & whole_bit_count_100) + ((~ all_zeros_196) & non_zeros_lzc_94));
  assign left_all_zeros_count_97 = (all_left_zeros_195 & _zz_544);
  assign x_tmp_199 = x_tmp_195[3 : 2];
  assign all_zeros_199 = ((! (x_tmp_199 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_199 = ((! (x_tmp_199[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_101 = 2'b10;
  assign left_bit_count_101 = 2'b01;
  assign _zz_100 = x_tmp_199[0 : 0];
  assign x_tmp_200 = _zz_100;
  assign all_zeros_200 = ((! (x_tmp_200 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_200 = ((! (x_tmp_200[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_98 = (all_left_zeros_199 & _zz_546);
  assign _zz_101 = x_tmp_199[1 : 1];
  assign x_tmp_201 = _zz_101;
  assign all_zeros_201 = ((! (x_tmp_201 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_201 = ((! (x_tmp_201[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_95 = ((~ all_left_zeros_199) & _zz_550);
  assign non_zeros_lzc_95 = (left_all_zeros_count_98 + left_non_all_zeros_count_95);
  assign ret_199 = ((all_zeros_199 & whole_bit_count_101) + ((~ all_zeros_199) & non_zeros_lzc_95));
  assign left_non_all_zeros_count_96 = ((~ all_left_zeros_195) & _zz_551);
  assign non_zeros_lzc_96 = (left_all_zeros_count_97 + left_non_all_zeros_count_96);
  assign ret_195 = ((all_zeros_195 & whole_bit_count_99) + ((~ all_zeros_195) & non_zeros_lzc_96));
  assign left_all_zeros_count_99 = (all_left_zeros_194 & _zz_552);
  assign x_tmp_202 = x_tmp_194[7 : 4];
  assign all_zeros_202 = ((! (x_tmp_202 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_202 = ((! (x_tmp_202[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_102 = 3'b100;
  assign left_bit_count_102 = 3'b010;
  assign x_tmp_203 = x_tmp_202[1 : 0];
  assign all_zeros_203 = ((! (x_tmp_203 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_203 = ((! (x_tmp_203[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_103 = 2'b10;
  assign left_bit_count_103 = 2'b01;
  assign _zz_102 = x_tmp_203[0 : 0];
  assign x_tmp_204 = _zz_102;
  assign all_zeros_204 = ((! (x_tmp_204 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_204 = ((! (x_tmp_204[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_100 = (all_left_zeros_203 & _zz_554);
  assign _zz_103 = x_tmp_203[1 : 1];
  assign x_tmp_205 = _zz_103;
  assign all_zeros_205 = ((! (x_tmp_205 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_205 = ((! (x_tmp_205[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_97 = ((~ all_left_zeros_203) & _zz_558);
  assign non_zeros_lzc_97 = (left_all_zeros_count_100 + left_non_all_zeros_count_97);
  assign ret_203 = ((all_zeros_203 & whole_bit_count_103) + ((~ all_zeros_203) & non_zeros_lzc_97));
  assign left_all_zeros_count_101 = (all_left_zeros_202 & _zz_559);
  assign x_tmp_206 = x_tmp_202[3 : 2];
  assign all_zeros_206 = ((! (x_tmp_206 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_206 = ((! (x_tmp_206[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_104 = 2'b10;
  assign left_bit_count_104 = 2'b01;
  assign _zz_104 = x_tmp_206[0 : 0];
  assign x_tmp_207 = _zz_104;
  assign all_zeros_207 = ((! (x_tmp_207 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_207 = ((! (x_tmp_207[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_102 = (all_left_zeros_206 & _zz_561);
  assign _zz_105 = x_tmp_206[1 : 1];
  assign x_tmp_208 = _zz_105;
  assign all_zeros_208 = ((! (x_tmp_208 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_208 = ((! (x_tmp_208[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_98 = ((~ all_left_zeros_206) & _zz_565);
  assign non_zeros_lzc_98 = (left_all_zeros_count_102 + left_non_all_zeros_count_98);
  assign ret_206 = ((all_zeros_206 & whole_bit_count_104) + ((~ all_zeros_206) & non_zeros_lzc_98));
  assign left_non_all_zeros_count_99 = ((~ all_left_zeros_202) & _zz_566);
  assign non_zeros_lzc_99 = (left_all_zeros_count_101 + left_non_all_zeros_count_99);
  assign ret_202 = ((all_zeros_202 & whole_bit_count_102) + ((~ all_zeros_202) & non_zeros_lzc_99));
  assign left_non_all_zeros_count_100 = ((~ all_left_zeros_194) & _zz_567);
  assign non_zeros_lzc_100 = (left_all_zeros_count_99 + left_non_all_zeros_count_100);
  assign ret_194 = ((all_zeros_194 & whole_bit_count_98) + ((~ all_zeros_194) & non_zeros_lzc_100));
  assign left_all_zeros_count_103 = (all_left_zeros_193 & _zz_568);
  assign x_tmp_209 = x_tmp_193[15 : 8];
  assign all_zeros_209 = ((! (x_tmp_209 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_209 = ((! (x_tmp_209[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_105 = 4'b1000;
  assign left_bit_count_105 = 4'b0100;
  assign x_tmp_210 = x_tmp_209[3 : 0];
  assign all_zeros_210 = ((! (x_tmp_210 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_210 = ((! (x_tmp_210[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_106 = 3'b100;
  assign left_bit_count_106 = 3'b010;
  assign x_tmp_211 = x_tmp_210[1 : 0];
  assign all_zeros_211 = ((! (x_tmp_211 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_211 = ((! (x_tmp_211[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_107 = 2'b10;
  assign left_bit_count_107 = 2'b01;
  assign _zz_106 = x_tmp_211[0 : 0];
  assign x_tmp_212 = _zz_106;
  assign all_zeros_212 = ((! (x_tmp_212 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_212 = ((! (x_tmp_212[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_104 = (all_left_zeros_211 & _zz_570);
  assign _zz_107 = x_tmp_211[1 : 1];
  assign x_tmp_213 = _zz_107;
  assign all_zeros_213 = ((! (x_tmp_213 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_213 = ((! (x_tmp_213[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_101 = ((~ all_left_zeros_211) & _zz_574);
  assign non_zeros_lzc_101 = (left_all_zeros_count_104 + left_non_all_zeros_count_101);
  assign ret_211 = ((all_zeros_211 & whole_bit_count_107) + ((~ all_zeros_211) & non_zeros_lzc_101));
  assign left_all_zeros_count_105 = (all_left_zeros_210 & _zz_575);
  assign x_tmp_214 = x_tmp_210[3 : 2];
  assign all_zeros_214 = ((! (x_tmp_214 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_214 = ((! (x_tmp_214[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_108 = 2'b10;
  assign left_bit_count_108 = 2'b01;
  assign _zz_108 = x_tmp_214[0 : 0];
  assign x_tmp_215 = _zz_108;
  assign all_zeros_215 = ((! (x_tmp_215 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_215 = ((! (x_tmp_215[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_106 = (all_left_zeros_214 & _zz_577);
  assign _zz_109 = x_tmp_214[1 : 1];
  assign x_tmp_216 = _zz_109;
  assign all_zeros_216 = ((! (x_tmp_216 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_216 = ((! (x_tmp_216[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_102 = ((~ all_left_zeros_214) & _zz_581);
  assign non_zeros_lzc_102 = (left_all_zeros_count_106 + left_non_all_zeros_count_102);
  assign ret_214 = ((all_zeros_214 & whole_bit_count_108) + ((~ all_zeros_214) & non_zeros_lzc_102));
  assign left_non_all_zeros_count_103 = ((~ all_left_zeros_210) & _zz_582);
  assign non_zeros_lzc_103 = (left_all_zeros_count_105 + left_non_all_zeros_count_103);
  assign ret_210 = ((all_zeros_210 & whole_bit_count_106) + ((~ all_zeros_210) & non_zeros_lzc_103));
  assign left_all_zeros_count_107 = (all_left_zeros_209 & _zz_583);
  assign x_tmp_217 = x_tmp_209[7 : 4];
  assign all_zeros_217 = ((! (x_tmp_217 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_217 = ((! (x_tmp_217[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_109 = 3'b100;
  assign left_bit_count_109 = 3'b010;
  assign x_tmp_218 = x_tmp_217[1 : 0];
  assign all_zeros_218 = ((! (x_tmp_218 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_218 = ((! (x_tmp_218[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_110 = 2'b10;
  assign left_bit_count_110 = 2'b01;
  assign _zz_110 = x_tmp_218[0 : 0];
  assign x_tmp_219 = _zz_110;
  assign all_zeros_219 = ((! (x_tmp_219 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_219 = ((! (x_tmp_219[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_108 = (all_left_zeros_218 & _zz_585);
  assign _zz_111 = x_tmp_218[1 : 1];
  assign x_tmp_220 = _zz_111;
  assign all_zeros_220 = ((! (x_tmp_220 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_220 = ((! (x_tmp_220[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_104 = ((~ all_left_zeros_218) & _zz_589);
  assign non_zeros_lzc_104 = (left_all_zeros_count_108 + left_non_all_zeros_count_104);
  assign ret_218 = ((all_zeros_218 & whole_bit_count_110) + ((~ all_zeros_218) & non_zeros_lzc_104));
  assign left_all_zeros_count_109 = (all_left_zeros_217 & _zz_590);
  assign x_tmp_221 = x_tmp_217[3 : 2];
  assign all_zeros_221 = ((! (x_tmp_221 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_221 = ((! (x_tmp_221[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_111 = 2'b10;
  assign left_bit_count_111 = 2'b01;
  assign _zz_112 = x_tmp_221[0 : 0];
  assign x_tmp_222 = _zz_112;
  assign all_zeros_222 = ((! (x_tmp_222 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_222 = ((! (x_tmp_222[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_110 = (all_left_zeros_221 & _zz_592);
  assign _zz_113 = x_tmp_221[1 : 1];
  assign x_tmp_223 = _zz_113;
  assign all_zeros_223 = ((! (x_tmp_223 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_223 = ((! (x_tmp_223[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_105 = ((~ all_left_zeros_221) & _zz_596);
  assign non_zeros_lzc_105 = (left_all_zeros_count_110 + left_non_all_zeros_count_105);
  assign ret_221 = ((all_zeros_221 & whole_bit_count_111) + ((~ all_zeros_221) & non_zeros_lzc_105));
  assign left_non_all_zeros_count_106 = ((~ all_left_zeros_217) & _zz_597);
  assign non_zeros_lzc_106 = (left_all_zeros_count_109 + left_non_all_zeros_count_106);
  assign ret_217 = ((all_zeros_217 & whole_bit_count_109) + ((~ all_zeros_217) & non_zeros_lzc_106));
  assign left_non_all_zeros_count_107 = ((~ all_left_zeros_209) & _zz_598);
  assign non_zeros_lzc_107 = (left_all_zeros_count_107 + left_non_all_zeros_count_107);
  assign ret_209 = ((all_zeros_209 & whole_bit_count_105) + ((~ all_zeros_209) & non_zeros_lzc_107));
  assign left_non_all_zeros_count_108 = ((~ all_left_zeros_193) & _zz_599);
  assign non_zeros_lzc_108 = (left_all_zeros_count_103 + left_non_all_zeros_count_108);
  assign ret_193 = ((all_zeros_193 & whole_bit_count_97) + ((~ all_zeros_193) & non_zeros_lzc_108));
  assign left_all_zeros_count_111 = (all_left_zeros_192 & _zz_600);
  assign x_tmp_224 = x_tmp_192[31 : 16];
  assign all_zeros_224 = ((! (x_tmp_224 != 16'h0)) ? 5'h1f : 5'h0);
  assign all_left_zeros_224 = ((! (x_tmp_224[15 : 8] != 8'h0)) ? 5'h1f : 5'h0);
  assign whole_bit_count_112 = 5'h10;
  assign left_bit_count_112 = 5'h08;
  assign x_tmp_225 = x_tmp_224[7 : 0];
  assign all_zeros_225 = ((! (x_tmp_225 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_225 = ((! (x_tmp_225[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_113 = 4'b1000;
  assign left_bit_count_113 = 4'b0100;
  assign x_tmp_226 = x_tmp_225[3 : 0];
  assign all_zeros_226 = ((! (x_tmp_226 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_226 = ((! (x_tmp_226[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_114 = 3'b100;
  assign left_bit_count_114 = 3'b010;
  assign x_tmp_227 = x_tmp_226[1 : 0];
  assign all_zeros_227 = ((! (x_tmp_227 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_227 = ((! (x_tmp_227[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_115 = 2'b10;
  assign left_bit_count_115 = 2'b01;
  assign _zz_114 = x_tmp_227[0 : 0];
  assign x_tmp_228 = _zz_114;
  assign all_zeros_228 = ((! (x_tmp_228 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_228 = ((! (x_tmp_228[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_112 = (all_left_zeros_227 & _zz_602);
  assign _zz_115 = x_tmp_227[1 : 1];
  assign x_tmp_229 = _zz_115;
  assign all_zeros_229 = ((! (x_tmp_229 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_229 = ((! (x_tmp_229[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_109 = ((~ all_left_zeros_227) & _zz_606);
  assign non_zeros_lzc_109 = (left_all_zeros_count_112 + left_non_all_zeros_count_109);
  assign ret_227 = ((all_zeros_227 & whole_bit_count_115) + ((~ all_zeros_227) & non_zeros_lzc_109));
  assign left_all_zeros_count_113 = (all_left_zeros_226 & _zz_607);
  assign x_tmp_230 = x_tmp_226[3 : 2];
  assign all_zeros_230 = ((! (x_tmp_230 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_230 = ((! (x_tmp_230[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_116 = 2'b10;
  assign left_bit_count_116 = 2'b01;
  assign _zz_116 = x_tmp_230[0 : 0];
  assign x_tmp_231 = _zz_116;
  assign all_zeros_231 = ((! (x_tmp_231 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_231 = ((! (x_tmp_231[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_114 = (all_left_zeros_230 & _zz_609);
  assign _zz_117 = x_tmp_230[1 : 1];
  assign x_tmp_232 = _zz_117;
  assign all_zeros_232 = ((! (x_tmp_232 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_232 = ((! (x_tmp_232[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_110 = ((~ all_left_zeros_230) & _zz_613);
  assign non_zeros_lzc_110 = (left_all_zeros_count_114 + left_non_all_zeros_count_110);
  assign ret_230 = ((all_zeros_230 & whole_bit_count_116) + ((~ all_zeros_230) & non_zeros_lzc_110));
  assign left_non_all_zeros_count_111 = ((~ all_left_zeros_226) & _zz_614);
  assign non_zeros_lzc_111 = (left_all_zeros_count_113 + left_non_all_zeros_count_111);
  assign ret_226 = ((all_zeros_226 & whole_bit_count_114) + ((~ all_zeros_226) & non_zeros_lzc_111));
  assign left_all_zeros_count_115 = (all_left_zeros_225 & _zz_615);
  assign x_tmp_233 = x_tmp_225[7 : 4];
  assign all_zeros_233 = ((! (x_tmp_233 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_233 = ((! (x_tmp_233[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_117 = 3'b100;
  assign left_bit_count_117 = 3'b010;
  assign x_tmp_234 = x_tmp_233[1 : 0];
  assign all_zeros_234 = ((! (x_tmp_234 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_234 = ((! (x_tmp_234[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_118 = 2'b10;
  assign left_bit_count_118 = 2'b01;
  assign _zz_118 = x_tmp_234[0 : 0];
  assign x_tmp_235 = _zz_118;
  assign all_zeros_235 = ((! (x_tmp_235 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_235 = ((! (x_tmp_235[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_116 = (all_left_zeros_234 & _zz_617);
  assign _zz_119 = x_tmp_234[1 : 1];
  assign x_tmp_236 = _zz_119;
  assign all_zeros_236 = ((! (x_tmp_236 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_236 = ((! (x_tmp_236[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_112 = ((~ all_left_zeros_234) & _zz_621);
  assign non_zeros_lzc_112 = (left_all_zeros_count_116 + left_non_all_zeros_count_112);
  assign ret_234 = ((all_zeros_234 & whole_bit_count_118) + ((~ all_zeros_234) & non_zeros_lzc_112));
  assign left_all_zeros_count_117 = (all_left_zeros_233 & _zz_622);
  assign x_tmp_237 = x_tmp_233[3 : 2];
  assign all_zeros_237 = ((! (x_tmp_237 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_237 = ((! (x_tmp_237[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_119 = 2'b10;
  assign left_bit_count_119 = 2'b01;
  assign _zz_120 = x_tmp_237[0 : 0];
  assign x_tmp_238 = _zz_120;
  assign all_zeros_238 = ((! (x_tmp_238 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_238 = ((! (x_tmp_238[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_118 = (all_left_zeros_237 & _zz_624);
  assign _zz_121 = x_tmp_237[1 : 1];
  assign x_tmp_239 = _zz_121;
  assign all_zeros_239 = ((! (x_tmp_239 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_239 = ((! (x_tmp_239[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_113 = ((~ all_left_zeros_237) & _zz_628);
  assign non_zeros_lzc_113 = (left_all_zeros_count_118 + left_non_all_zeros_count_113);
  assign ret_237 = ((all_zeros_237 & whole_bit_count_119) + ((~ all_zeros_237) & non_zeros_lzc_113));
  assign left_non_all_zeros_count_114 = ((~ all_left_zeros_233) & _zz_629);
  assign non_zeros_lzc_114 = (left_all_zeros_count_117 + left_non_all_zeros_count_114);
  assign ret_233 = ((all_zeros_233 & whole_bit_count_117) + ((~ all_zeros_233) & non_zeros_lzc_114));
  assign left_non_all_zeros_count_115 = ((~ all_left_zeros_225) & _zz_630);
  assign non_zeros_lzc_115 = (left_all_zeros_count_115 + left_non_all_zeros_count_115);
  assign ret_225 = ((all_zeros_225 & whole_bit_count_113) + ((~ all_zeros_225) & non_zeros_lzc_115));
  assign left_all_zeros_count_119 = (all_left_zeros_224 & _zz_631);
  assign x_tmp_240 = x_tmp_224[15 : 8];
  assign all_zeros_240 = ((! (x_tmp_240 != 8'h0)) ? 4'b1111 : 4'b0000);
  assign all_left_zeros_240 = ((! (x_tmp_240[7 : 4] != 4'b0000)) ? 4'b1111 : 4'b0000);
  assign whole_bit_count_120 = 4'b1000;
  assign left_bit_count_120 = 4'b0100;
  assign x_tmp_241 = x_tmp_240[3 : 0];
  assign all_zeros_241 = ((! (x_tmp_241 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_241 = ((! (x_tmp_241[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_121 = 3'b100;
  assign left_bit_count_121 = 3'b010;
  assign x_tmp_242 = x_tmp_241[1 : 0];
  assign all_zeros_242 = ((! (x_tmp_242 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_242 = ((! (x_tmp_242[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_122 = 2'b10;
  assign left_bit_count_122 = 2'b01;
  assign _zz_122 = x_tmp_242[0 : 0];
  assign x_tmp_243 = _zz_122;
  assign all_zeros_243 = ((! (x_tmp_243 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_243 = ((! (x_tmp_243[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_120 = (all_left_zeros_242 & _zz_633);
  assign _zz_123 = x_tmp_242[1 : 1];
  assign x_tmp_244 = _zz_123;
  assign all_zeros_244 = ((! (x_tmp_244 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_244 = ((! (x_tmp_244[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_116 = ((~ all_left_zeros_242) & _zz_637);
  assign non_zeros_lzc_116 = (left_all_zeros_count_120 + left_non_all_zeros_count_116);
  assign ret_242 = ((all_zeros_242 & whole_bit_count_122) + ((~ all_zeros_242) & non_zeros_lzc_116));
  assign left_all_zeros_count_121 = (all_left_zeros_241 & _zz_638);
  assign x_tmp_245 = x_tmp_241[3 : 2];
  assign all_zeros_245 = ((! (x_tmp_245 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_245 = ((! (x_tmp_245[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_123 = 2'b10;
  assign left_bit_count_123 = 2'b01;
  assign _zz_124 = x_tmp_245[0 : 0];
  assign x_tmp_246 = _zz_124;
  assign all_zeros_246 = ((! (x_tmp_246 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_246 = ((! (x_tmp_246[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_122 = (all_left_zeros_245 & _zz_640);
  assign _zz_125 = x_tmp_245[1 : 1];
  assign x_tmp_247 = _zz_125;
  assign all_zeros_247 = ((! (x_tmp_247 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_247 = ((! (x_tmp_247[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_117 = ((~ all_left_zeros_245) & _zz_644);
  assign non_zeros_lzc_117 = (left_all_zeros_count_122 + left_non_all_zeros_count_117);
  assign ret_245 = ((all_zeros_245 & whole_bit_count_123) + ((~ all_zeros_245) & non_zeros_lzc_117));
  assign left_non_all_zeros_count_118 = ((~ all_left_zeros_241) & _zz_645);
  assign non_zeros_lzc_118 = (left_all_zeros_count_121 + left_non_all_zeros_count_118);
  assign ret_241 = ((all_zeros_241 & whole_bit_count_121) + ((~ all_zeros_241) & non_zeros_lzc_118));
  assign left_all_zeros_count_123 = (all_left_zeros_240 & _zz_646);
  assign x_tmp_248 = x_tmp_240[7 : 4];
  assign all_zeros_248 = ((! (x_tmp_248 != 4'b0000)) ? 3'b111 : 3'b000);
  assign all_left_zeros_248 = ((! (x_tmp_248[3 : 2] != 2'b00)) ? 3'b111 : 3'b000);
  assign whole_bit_count_124 = 3'b100;
  assign left_bit_count_124 = 3'b010;
  assign x_tmp_249 = x_tmp_248[1 : 0];
  assign all_zeros_249 = ((! (x_tmp_249 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_249 = ((! (x_tmp_249[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_125 = 2'b10;
  assign left_bit_count_125 = 2'b01;
  assign _zz_126 = x_tmp_249[0 : 0];
  assign x_tmp_250 = _zz_126;
  assign all_zeros_250 = ((! (x_tmp_250 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_250 = ((! (x_tmp_250[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_124 = (all_left_zeros_249 & _zz_648);
  assign _zz_127 = x_tmp_249[1 : 1];
  assign x_tmp_251 = _zz_127;
  assign all_zeros_251 = ((! (x_tmp_251 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_251 = ((! (x_tmp_251[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_119 = ((~ all_left_zeros_249) & _zz_652);
  assign non_zeros_lzc_119 = (left_all_zeros_count_124 + left_non_all_zeros_count_119);
  assign ret_249 = ((all_zeros_249 & whole_bit_count_125) + ((~ all_zeros_249) & non_zeros_lzc_119));
  assign left_all_zeros_count_125 = (all_left_zeros_248 & _zz_653);
  assign x_tmp_252 = x_tmp_248[3 : 2];
  assign all_zeros_252 = ((! (x_tmp_252 != 2'b00)) ? 2'b11 : 2'b00);
  assign all_left_zeros_252 = ((! (x_tmp_252[1 : 1] != 1'b0)) ? 2'b11 : 2'b00);
  assign whole_bit_count_126 = 2'b10;
  assign left_bit_count_126 = 2'b01;
  assign _zz_128 = x_tmp_252[0 : 0];
  assign x_tmp_253 = _zz_128;
  assign all_zeros_253 = ((! (x_tmp_253 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_253 = ((! (x_tmp_253[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_all_zeros_count_126 = (all_left_zeros_252 & _zz_655);
  assign _zz_129 = x_tmp_252[1 : 1];
  assign x_tmp_254 = _zz_129;
  assign all_zeros_254 = ((! (x_tmp_254 != 1'b0)) ? 1'b1 : 1'b0);
  assign all_left_zeros_254 = ((! (x_tmp_254[0 : 0] != 1'b0)) ? 1'b1 : 1'b0);
  assign left_non_all_zeros_count_120 = ((~ all_left_zeros_252) & _zz_659);
  assign non_zeros_lzc_120 = (left_all_zeros_count_126 + left_non_all_zeros_count_120);
  assign ret_252 = ((all_zeros_252 & whole_bit_count_126) + ((~ all_zeros_252) & non_zeros_lzc_120));
  assign left_non_all_zeros_count_121 = ((~ all_left_zeros_248) & _zz_660);
  assign non_zeros_lzc_121 = (left_all_zeros_count_125 + left_non_all_zeros_count_121);
  assign ret_248 = ((all_zeros_248 & whole_bit_count_124) + ((~ all_zeros_248) & non_zeros_lzc_121));
  assign left_non_all_zeros_count_122 = ((~ all_left_zeros_240) & _zz_661);
  assign non_zeros_lzc_122 = (left_all_zeros_count_123 + left_non_all_zeros_count_122);
  assign ret_240 = ((all_zeros_240 & whole_bit_count_120) + ((~ all_zeros_240) & non_zeros_lzc_122));
  assign left_non_all_zeros_count_123 = ((~ all_left_zeros_224) & _zz_662);
  assign non_zeros_lzc_123 = (left_all_zeros_count_119 + left_non_all_zeros_count_123);
  assign ret_224 = ((all_zeros_224 & whole_bit_count_112) + ((~ all_zeros_224) & non_zeros_lzc_123));
  assign left_non_all_zeros_count_124 = ((~ all_left_zeros_192) & _zz_663);
  assign non_zeros_lzc_124 = (left_all_zeros_count_111 + left_non_all_zeros_count_124);
  assign ret_192 = ((all_zeros_192 & whole_bit_count_96) + ((~ all_zeros_192) & non_zeros_lzc_124));
  assign left_non_all_zeros_count_125 = ((~ all_left_zeros_128) & _zz_664);
  assign non_zeros_lzc_125 = (left_all_zeros_count_95 + left_non_all_zeros_count_125);
  assign ret_128 = ((all_zeros_128 & whole_bit_count_64) + ((~ all_zeros_128) & non_zeros_lzc_125));
  assign left_non_all_zeros_count_126 = ((~ all_left_zeros) & _zz_665);
  assign non_zeros_lzc_126 = (left_all_zeros_count_63 + left_non_all_zeros_count_126);
  assign ret = ((all_zeros & whole_bit_count) + ((~ all_zeros) & non_zeros_lzc_126));
  assign lzc = lzc_t[6:0];
  assign position = (lzc - 7'h01);
  assign x1 = _zz_666[31 : 0];
  assign x2 = _zz_667[31 : 0];
  assign y1_real = _zz_146;
  assign y1_imag = _zz_147;
  assign y2_real = _zz_149;
  assign y2_imag = _zz_150;
  assign xd = ($signed(x1) - $signed(x2));
  assign yd_real = ($signed(y1_real) - $signed(y2_real));
  assign yd_imag = ($signed(y1_imag) - $signed(y2_imag));
  assign _zz_132 = yd_real;
  assign _zz_133 = yd_imag;
  assign _zz_134 = xd;
  assign _zz_130 = _zz_668[31 : 0];
  always @ (*) begin
    if(_zz_151)begin
      k_real = _zz_669[31 : 0];
    end else begin
      k_real = _zz_675[31 : 0];
    end
  end

  always @ (*) begin
    if(_zz_151)begin
      k_imag = _zz_670[31 : 0];
    end else begin
      k_imag = _zz_680[31 : 0];
    end
  end

  assign _zz_135 = _zz_671[31 : 0];
  assign _zz_136 = _zz_130;
  assign _zz_137 = ($signed(_zz_672) / $signed(_zz_674));
  assign _zz_138 = _zz_676[31 : 0];
  assign _zz_139 = _zz_130;
  assign _zz_140 = ($signed(_zz_677) / $signed(_zz_679));
  assign deltax_real = ($signed(indx) - $signed(x1));
  assign deltax_imag = 32'h0;
  assign _zz_131 = ($signed(_zz_681) * $signed(deltax_real));
  assign _zz_141 = _zz_682;
  assign _zz_142 = _zz_685;
  assign pre_exp_indx_real = ($signed(_zz_688) + $signed(_zz_691));
  assign pre_exp_indx_imag = ($signed(_zz_692) + $signed(_zz_695));
  assign _zz_143 = pre_exp_indx_regNext_real;
  always @ (*) begin
    if(_zz_152)begin
      exp_func_value_real = _zz_696[31 : 0];
    end else begin
      if(_zz_153)begin
        exp_func_value_real = pfunc_tb_127_regNext_real;
      end else begin
        exp_func_value_real = pfunc_tb_0_regNext_real;
      end
    end
  end

  assign _zz_144 = pre_exp_indx_regNext_imag;
  always @ (*) begin
    if(_zz_152)begin
      exp_func_value_imag = _zz_697[31 : 0];
    end else begin
      if(_zz_153)begin
        exp_func_value_imag = pfunc_tb_127_regNext_imag;
      end else begin
        exp_func_value_imag = pfunc_tb_0_regNext_imag;
      end
    end
  end

  assign io_data_out_real = exp_func_value_regNext_real;
  assign io_data_out_imag = exp_func_value_regNext_imag;
  always @ (posedge clk) begin
    lzc_t <= ret;
    exp_func_value_regNext_real <= exp_func_value_real;
    exp_func_value_regNext_imag <= exp_func_value_imag;
  end

  always @ (posedge clk) begin
    pre_exp_indx_regNext_real <= pre_exp_indx_real;
    pre_exp_indx_regNext_imag <= pre_exp_indx_imag;
  end

  always @ (posedge clk) begin
    pfunc_tb_127_regNext_real <= pfunc_tb_127_real;
    pfunc_tb_127_regNext_imag <= pfunc_tb_127_imag;
  end

  always @ (posedge clk) begin
    pfunc_tb_0_regNext_real <= pfunc_tb_0_real;
    pfunc_tb_0_regNext_imag <= pfunc_tb_0_imag;
  end


endmodule

//SInt64fixTo47_16_ROUNDTOINF replaced by SInt64fixTo47_16_ROUNDTOINF

//SInt64fixTo47_16_ROUNDTOINF replaced by SInt64fixTo47_16_ROUNDTOINF

//SInt64fixTo63_0_ROUNDTOINF replaced by SInt64fixTo63_0_ROUNDTOINF

module SInt64fixTo63_0_ROUNDTOINF (
  input      [63:0]   din,
  output     [63:0]   dout
);

  assign dout = din;

endmodule

//SInt64fixTo47_16_ROUNDTOINF replaced by SInt64fixTo47_16_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

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

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

//SInt32fixTo31_0_ROUNDTOINF replaced by SInt32fixTo31_0_ROUNDTOINF

module SInt32fixTo31_0_ROUNDTOINF (
  input      [31:0]   din,
  output     [31:0]   dout
);

  assign dout = din;

endmodule
