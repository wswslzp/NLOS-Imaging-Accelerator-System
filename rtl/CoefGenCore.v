// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : CoefGenCore
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79
// Date      : 20/07/2020, 11:11:12
// Designer	: Zhengpeng Liao



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
