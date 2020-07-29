// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : Amodule
// Git hash  : ecd107b3e75511c4f655b3f6c248d95910a85fb1
// 草泥马


module Amodule (
  input               axi4_aw_valid,
  output              axi4_aw_ready,
  input      [31:0]   axi4_aw_payload_addr,
  input      [3:0]    axi4_aw_payload_id,
  input      [3:0]    axi4_aw_payload_region,
  input      [7:0]    axi4_aw_payload_len,
  input      [2:0]    axi4_aw_payload_size,
  input      [1:0]    axi4_aw_payload_burst,
  input      [0:0]    axi4_aw_payload_lock,
  input      [3:0]    axi4_aw_payload_cache,
  input      [3:0]    axi4_aw_payload_qos,
  input      [2:0]    axi4_aw_payload_prot,
  input               axi4_w_valid,
  output              axi4_w_ready,
  input      [31:0]   axi4_w_payload_data,
  input      [3:0]    axi4_w_payload_strb,
  input               axi4_w_payload_last,
  output              axi4_b_valid,
  input               axi4_b_ready,
  output     [3:0]    axi4_b_payload_id,
  output     [1:0]    axi4_b_payload_resp,
  output     [15:0]   b_real,
  output     [15:0]   b_imag,
  output     [7:0]    vb_r,
  output     [7:0]    vb_g,
  output     [7:0]    vb_b,
  input               clk,
  input               reset
);
  reg        [11:0]   _zz_2;
  wire       [11:0]   _zz_3;
  wire       [11:0]   _zz_4;
  wire       [11:0]   _zz_5;
  wire       [31:0]   _zz_6;
  wire       [15:0]   _zz_7;
  wire       [15:0]   _zz_8;
  wire       [31:0]   _zz_9;
  wire       [31:0]   _zz_10;
  wire       [15:0]   _zz_11;
  wire       [15:0]   _zz_12;
  wire       [31:0]   _zz_13;
  wire       [31:0]   _zz_14;
  wire       [7:0]    _zz_15;
  wire       [7:0]    _zz_16;
  wire       [31:0]   _zz_17;
  wire       [31:0]   _zz_18;
  wire       [7:0]    _zz_19;
  wire       [7:0]    _zz_20;
  wire       [31:0]   _zz_21;
  wire       [31:0]   _zz_22;
  wire       [7:0]    _zz_23;
  wire       [7:0]    _zz_24;
  wire       [31:0]   _zz_25;
  reg        [31:0]   aw_area_awaddr_r;
  reg        [7:0]    aw_area_awlen_r;
  reg        [2:0]    aw_area_awsize_r;
  reg        [3:0]    aw_area_awid_r;
  wire       [15:0]   a_real;
  wire       [15:0]   a_imag;
  wire       [7:0]    va_r;
  wire       [7:0]    va_g;
  wire       [7:0]    va_b;
  reg        [15:0]   a;
  reg        [15:0]   a_1;
  reg        [7:0]    va_reg_r;
  reg        [7:0]    va_reg_g;
  reg        [7:0]    va_reg_b;
  reg        [31:0]   load_data_area_current_addr;
  reg        [31:0]   Axi4Incr_result;
  wire       [19:0]   Axi4Incr_highCat;
  wire       [0:0]    Axi4Incr_sizeValue;
  wire       [11:0]   Axi4Incr_alignMask;
  wire       [11:0]   Axi4Incr_base;
  wire       [11:0]   Axi4Incr_baseIncr;
  reg        [1:0]    _zz_1;
  wire       [1:0]    Axi4Incr_wrapCase;
  reg        [31:0]   axi4_w_payload_data_regNext;

  assign _zz_3 = aw_area_awaddr_r[11 : 0];
  assign _zz_4 = _zz_3;
  assign _zz_5 = {11'd0, Axi4Incr_sizeValue};
  assign _zz_6 = (((32'h00000001 <= load_data_area_current_addr) && (load_data_area_current_addr < 32'h00000001)) ? axi4_w_payload_data_regNext : _zz_9);
  assign _zz_7 = _zz_6[15:0];
  assign _zz_8 = a_1;
  assign _zz_9 = {16'd0, _zz_8};
  assign _zz_10 = (((32'h0 <= load_data_area_current_addr) && (load_data_area_current_addr < 32'h0)) ? axi4_w_payload_data_regNext : _zz_13);
  assign _zz_11 = _zz_10[15:0];
  assign _zz_12 = a;
  assign _zz_13 = {16'd0, _zz_12};
  assign _zz_14 = (((32'h00000004 <= load_data_area_current_addr) && (load_data_area_current_addr < 32'h00000004)) ? axi4_w_payload_data_regNext : _zz_17);
  assign _zz_15 = _zz_14[7:0];
  assign _zz_16 = va_reg_b;
  assign _zz_17 = {24'd0, _zz_16};
  assign _zz_18 = (((32'h00000003 <= load_data_area_current_addr) && (load_data_area_current_addr < 32'h00000003)) ? axi4_w_payload_data_regNext : _zz_21);
  assign _zz_19 = _zz_18[7:0];
  assign _zz_20 = va_reg_g;
  assign _zz_21 = {24'd0, _zz_20};
  assign _zz_22 = (((32'h00000002 <= load_data_area_current_addr) && (load_data_area_current_addr < 32'h00000002)) ? axi4_w_payload_data_regNext : _zz_25);
  assign _zz_23 = _zz_22[7:0];
  assign _zz_24 = va_reg_r;
  assign _zz_25 = {24'd0, _zz_24};
  always @(*) begin
    case(Axi4Incr_wrapCase)
      2'b00 : begin
        _zz_2 = {Axi4Incr_base[11 : 1],Axi4Incr_baseIncr[0 : 0]};
      end
      2'b01 : begin
        _zz_2 = {Axi4Incr_base[11 : 2],Axi4Incr_baseIncr[1 : 0]};
      end
      2'b10 : begin
        _zz_2 = {Axi4Incr_base[11 : 3],Axi4Incr_baseIncr[2 : 0]};
      end
      default : begin
        _zz_2 = {Axi4Incr_base[11 : 4],Axi4Incr_baseIncr[3 : 0]};
      end
    endcase
  end

  assign axi4_b_valid = axi4_w_valid;
  assign axi4_b_payload_id = aw_area_awid_r;
  assign axi4_b_payload_resp = 2'b00;
  assign axi4_aw_ready = 1'b1;
  assign axi4_w_ready = 1'b1;
  assign Axi4Incr_highCat = aw_area_awaddr_r[31 : 12];
  assign Axi4Incr_sizeValue = 1'b1;
  assign Axi4Incr_alignMask = 12'h0;
  assign Axi4Incr_base = (_zz_4 & (~ Axi4Incr_alignMask));
  assign Axi4Incr_baseIncr = (Axi4Incr_base + _zz_5);
  always @ (*) begin
    if((((aw_area_awlen_r & 8'h08) == 8'h08))) begin
        _zz_1 = 2'b11;
    end else if((((aw_area_awlen_r & 8'h04) == 8'h04))) begin
        _zz_1 = 2'b10;
    end else if((((aw_area_awlen_r & 8'h02) == 8'h02))) begin
        _zz_1 = 2'b01;
    end else begin
        _zz_1 = 2'b00;
    end
  end

  assign Axi4Incr_wrapCase = (2'b00 + _zz_1);
  always @ (*) begin
    case(axi4_aw_payload_burst)
      2'b00 : begin
        Axi4Incr_result = aw_area_awaddr_r;
      end
      2'b10 : begin
        Axi4Incr_result = {Axi4Incr_highCat,_zz_2};
      end
      default : begin
        Axi4Incr_result = {Axi4Incr_highCat,Axi4Incr_baseIncr};
      end
    endcase
  end

  assign b_real = a;
  assign b_imag = a_1;
  assign vb_r = va_reg_r;
  assign vb_g = va_reg_g;
  assign vb_b = va_reg_b;
  always @ (posedge clk) begin
    if(axi4_aw_valid)begin
      aw_area_awaddr_r <= axi4_aw_payload_addr;
      aw_area_awlen_r <= axi4_aw_payload_len;
      aw_area_awsize_r <= axi4_aw_payload_size;
      aw_area_awid_r <= axi4_aw_payload_id;
    end
    if(axi4_w_valid)begin
      a_1 <= _zz_7;
      a <= _zz_11;
      va_reg_b <= _zz_15;
      va_reg_g <= _zz_19;
      va_reg_r <= _zz_23;
    end
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      load_data_area_current_addr <= 32'h0;
    end else begin
      if(axi4_w_valid)begin
        load_data_area_current_addr <= (load_data_area_current_addr + Axi4Incr_result);
      end
    end
  end

  always @ (posedge clk) begin
    axi4_w_payload_data_regNext <= axi4_w_payload_data;
  end


endmodule
