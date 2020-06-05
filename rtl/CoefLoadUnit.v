// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : CoefLoadUnit
// Git hash  : 41a80456602f177a5455b99e96004e737065612d



module CoefLoadUnit (
  input               axi4_aw_valid,
  output              axi4_aw_ready,
  input      [31:0]   axi4_aw_payload_addr,
  input      [3:0]    axi4_aw_payload_id,
  input      [7:0]    axi4_aw_payload_len,
  input      [2:0]    axi4_aw_payload_size,
  input      [1:0]    axi4_aw_payload_burst,
  input               axi4_w_valid,
  output              axi4_w_ready,
  input      [31:0]   axi4_w_payload_data,
  input      [3:0]    axi4_w_payload_strb,
  input               axi4_w_payload_last,
  output              axi4_b_valid,
  input               axi4_b_ready,
  output     [3:0]    axi4_b_payload_id,
  output     [1:0]    axi4_b_payload_resp,
  output              io_wave_valid,
  output     [15:0]   io_wave_payload_0_0,
  output     [15:0]   io_wave_payload_0_1,
  output     [15:0]   io_wave_payload_1_0,
  output     [15:0]   io_wave_payload_1_1,
  output              io_distance_valid,
  output     [15:0]   io_distance_payload_0_0,
  output     [15:0]   io_distance_payload_0_1,
  output     [15:0]   io_distance_payload_1_0,
  output     [15:0]   io_distance_payload_1_1,
  output              io_timeshift_valid,
  output     [15:0]   io_timeshift_payload_0_0_real,
  output     [15:0]   io_timeshift_payload_0_0_imag,
  output     [15:0]   io_timeshift_payload_0_1_real,
  output     [15:0]   io_timeshift_payload_0_1_imag,
  output     [15:0]   io_timeshift_payload_1_0_real,
  output     [15:0]   io_timeshift_payload_1_0_imag,
  output     [15:0]   io_timeshift_payload_1_1_real,
  output     [15:0]   io_timeshift_payload_1_1_imag,
  input               clk,
  input               reset
);
  reg        [11:0]   _zz_2;
  wire       [11:0]   _zz_3;
  wire       [11:0]   _zz_4;
  wire       [11:0]   _zz_5;
  wire       [0:0]    _zz_6;
  wire       [31:0]   _zz_7;
  wire       [15:0]   _zz_8;
  wire       [15:0]   _zz_9;
  wire       [31:0]   _zz_10;
  wire       [31:0]   _zz_11;
  wire       [15:0]   _zz_12;
  wire       [15:0]   _zz_13;
  wire       [31:0]   _zz_14;
  wire       [31:0]   _zz_15;
  wire       [15:0]   _zz_16;
  wire       [15:0]   _zz_17;
  wire       [31:0]   _zz_18;
  wire       [31:0]   _zz_19;
  wire       [15:0]   _zz_20;
  wire       [15:0]   _zz_21;
  wire       [31:0]   _zz_22;
  wire       [31:0]   _zz_23;
  wire       [15:0]   _zz_24;
  wire       [15:0]   _zz_25;
  wire       [31:0]   _zz_26;
  wire       [31:0]   _zz_27;
  wire       [15:0]   _zz_28;
  wire       [15:0]   _zz_29;
  wire       [31:0]   _zz_30;
  wire       [31:0]   _zz_31;
  wire       [15:0]   _zz_32;
  wire       [15:0]   _zz_33;
  wire       [31:0]   _zz_34;
  wire       [31:0]   _zz_35;
  wire       [15:0]   _zz_36;
  wire       [15:0]   _zz_37;
  wire       [31:0]   _zz_38;
  wire       [31:0]   _zz_39;
  wire       [15:0]   _zz_40;
  wire       [15:0]   _zz_41;
  wire       [31:0]   _zz_42;
  wire       [31:0]   _zz_43;
  wire       [15:0]   _zz_44;
  wire       [15:0]   _zz_45;
  wire       [31:0]   _zz_46;
  wire       [31:0]   _zz_47;
  wire       [15:0]   _zz_48;
  wire       [15:0]   _zz_49;
  wire       [31:0]   _zz_50;
  wire       [31:0]   _zz_51;
  wire       [15:0]   _zz_52;
  wire       [15:0]   _zz_53;
  wire       [31:0]   _zz_54;
  wire       [31:0]   _zz_55;
  wire       [15:0]   _zz_56;
  wire       [15:0]   _zz_57;
  wire       [31:0]   _zz_58;
  wire       [31:0]   _zz_59;
  wire       [15:0]   _zz_60;
  wire       [15:0]   _zz_61;
  wire       [31:0]   _zz_62;
  wire       [31:0]   _zz_63;
  wire       [15:0]   _zz_64;
  wire       [15:0]   _zz_65;
  wire       [31:0]   _zz_66;
  wire       [31:0]   _zz_67;
  wire       [15:0]   _zz_68;
  wire       [15:0]   _zz_69;
  wire       [31:0]   _zz_70;
  reg        [31:0]   aw_area_awaddr_r;
  reg        [7:0]    aw_area_awlen_r;
  reg        [2:0]    aw_area_awsize_r;
  reg        [3:0]    aw_area_awid_r;
  reg                 transfer_done;
  reg        [15:0]   wave_regs_0;
  reg        [15:0]   wave_regs_1;
  reg        [15:0]   wave_regs_2;
  reg        [15:0]   wave_regs_3;
  reg        [15:0]   distance_regs_0;
  reg        [15:0]   distance_regs_1;
  reg        [15:0]   distance_regs_2;
  reg        [15:0]   distance_regs_3;
  reg        [15:0]   timeshift_regs_0_real;
  reg        [15:0]   timeshift_regs_0_imag;
  reg        [15:0]   timeshift_regs_1_real;
  reg        [15:0]   timeshift_regs_1_imag;
  reg        [15:0]   timeshift_regs_2_real;
  reg        [15:0]   timeshift_regs_2_imag;
  reg        [15:0]   timeshift_regs_3_real;
  reg        [15:0]   timeshift_regs_3_imag;
  reg                 load_data_area_wvalid;
  reg        [31:0]   load_data_area_current_addr;
  reg        [31:0]   Axi4Incr_result;
  wire       [19:0]   Axi4Incr_highCat;
  wire       [0:0]    Axi4Incr_sizeValue;
  wire       [11:0]   Axi4Incr_alignMask;
  wire       [11:0]   Axi4Incr_base;
  wire       [11:0]   Axi4Incr_baseIncr;
  reg        [1:0]    _zz_1;
  wire       [1:0]    Axi4Incr_wrapCase;
  reg        [31:0]   wdata_r;
  wire                addr_hit;
  wire                addr_hit_1;
  wire                addr_hit_2;
  wire                addr_hit_3;
  wire                addr_hit_4;
  wire                addr_hit_5;
  wire                addr_hit_6;
  wire                addr_hit_7;
  wire                addr_hit_8;
  wire                addr_hit_9;
  wire                addr_hit_10;
  wire                addr_hit_11;
  wire                addr_hit_12;
  wire                addr_hit_13;
  wire                addr_hit_14;
  wire                addr_hit_15;
  wire                addr_hit_16;

  assign _zz_3 = aw_area_awaddr_r[11 : 0];
  assign _zz_4 = _zz_3;
  assign _zz_5 = {11'd0, Axi4Incr_sizeValue};
  assign _zz_6 = transfer_done;
  assign _zz_7 = (addr_hit_1 ? wdata_r : _zz_10);
  assign _zz_8 = _zz_7[15:0];
  assign _zz_9 = wave_regs_3;
  assign _zz_10 = {16'd0, _zz_9};
  assign _zz_11 = (addr_hit_2 ? wdata_r : _zz_14);
  assign _zz_12 = _zz_11[15:0];
  assign _zz_13 = wave_regs_0;
  assign _zz_14 = {16'd0, _zz_13};
  assign _zz_15 = (addr_hit_3 ? wdata_r : _zz_18);
  assign _zz_16 = _zz_15[15:0];
  assign _zz_17 = wave_regs_2;
  assign _zz_18 = {16'd0, _zz_17};
  assign _zz_19 = (addr_hit_4 ? wdata_r : _zz_22);
  assign _zz_20 = _zz_19[15:0];
  assign _zz_21 = wave_regs_1;
  assign _zz_22 = {16'd0, _zz_21};
  assign _zz_23 = (addr_hit_5 ? wdata_r : _zz_26);
  assign _zz_24 = _zz_23[15:0];
  assign _zz_25 = distance_regs_0;
  assign _zz_26 = {16'd0, _zz_25};
  assign _zz_27 = (addr_hit_6 ? wdata_r : _zz_30);
  assign _zz_28 = _zz_27[15:0];
  assign _zz_29 = distance_regs_1;
  assign _zz_30 = {16'd0, _zz_29};
  assign _zz_31 = (addr_hit_7 ? wdata_r : _zz_34);
  assign _zz_32 = _zz_31[15:0];
  assign _zz_33 = distance_regs_2;
  assign _zz_34 = {16'd0, _zz_33};
  assign _zz_35 = (addr_hit_8 ? wdata_r : _zz_38);
  assign _zz_36 = _zz_35[15:0];
  assign _zz_37 = distance_regs_3;
  assign _zz_38 = {16'd0, _zz_37};
  assign _zz_39 = (addr_hit_9 ? wdata_r : _zz_42);
  assign _zz_40 = _zz_39[15:0];
  assign _zz_41 = timeshift_regs_3_imag;
  assign _zz_42 = {16'd0, _zz_41};
  assign _zz_43 = (addr_hit_10 ? wdata_r : _zz_46);
  assign _zz_44 = _zz_43[15:0];
  assign _zz_45 = timeshift_regs_2_real;
  assign _zz_46 = {16'd0, _zz_45};
  assign _zz_47 = (addr_hit_11 ? wdata_r : _zz_50);
  assign _zz_48 = _zz_47[15:0];
  assign _zz_49 = timeshift_regs_0_real;
  assign _zz_50 = {16'd0, _zz_49};
  assign _zz_51 = (addr_hit_12 ? wdata_r : _zz_54);
  assign _zz_52 = _zz_51[15:0];
  assign _zz_53 = timeshift_regs_3_real;
  assign _zz_54 = {16'd0, _zz_53};
  assign _zz_55 = (addr_hit_13 ? wdata_r : _zz_58);
  assign _zz_56 = _zz_55[15:0];
  assign _zz_57 = timeshift_regs_1_real;
  assign _zz_58 = {16'd0, _zz_57};
  assign _zz_59 = (addr_hit_14 ? wdata_r : _zz_62);
  assign _zz_60 = _zz_59[15:0];
  assign _zz_61 = timeshift_regs_0_imag;
  assign _zz_62 = {16'd0, _zz_61};
  assign _zz_63 = (addr_hit_15 ? wdata_r : _zz_66);
  assign _zz_64 = _zz_63[15:0];
  assign _zz_65 = timeshift_regs_2_imag;
  assign _zz_66 = {16'd0, _zz_65};
  assign _zz_67 = (addr_hit_16 ? wdata_r : _zz_70);
  assign _zz_68 = _zz_67[15:0];
  assign _zz_69 = timeshift_regs_1_imag;
  assign _zz_70 = {16'd0, _zz_69};
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

  assign addr_hit = ((32'h0 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0));
  assign addr_hit_1 = ((32'h00000004 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000004));
  assign addr_hit_2 = ((32'h00000001 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000001));
  assign addr_hit_3 = ((32'h00000003 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000003));
  assign addr_hit_4 = ((32'h00000002 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000002));
  assign addr_hit_5 = ((32'h00000005 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000005));
  assign addr_hit_6 = ((32'h00000006 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000006));
  assign addr_hit_7 = ((32'h00000007 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000007));
  assign addr_hit_8 = ((32'h00000008 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000008));
  assign addr_hit_9 = ((32'h00000010 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000010));
  assign addr_hit_10 = ((32'h0000000d <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000d));
  assign addr_hit_11 = ((32'h00000009 <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h00000009));
  assign addr_hit_12 = ((32'h0000000f <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000f));
  assign addr_hit_13 = ((32'h0000000b <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000b));
  assign addr_hit_14 = ((32'h0000000a <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000a));
  assign addr_hit_15 = ((32'h0000000e <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000e));
  assign addr_hit_16 = ((32'h0000000c <= load_data_area_current_addr) && (load_data_area_current_addr <= 32'h0000000c));
  assign io_wave_payload_0_0 = wave_regs_0;
  assign io_wave_payload_1_0 = wave_regs_2;
  assign io_distance_payload_0_0 = distance_regs_0;
  assign io_timeshift_payload_0_0_real = timeshift_regs_0_real;
  assign io_timeshift_payload_0_0_imag = timeshift_regs_0_imag;
  assign io_distance_payload_1_0 = distance_regs_2;
  assign io_timeshift_payload_1_0_real = timeshift_regs_2_real;
  assign io_timeshift_payload_1_0_imag = timeshift_regs_2_imag;
  assign io_wave_payload_0_1 = wave_regs_1;
  assign io_wave_payload_1_1 = wave_regs_3;
  assign io_distance_payload_0_1 = distance_regs_1;
  assign io_timeshift_payload_0_1_real = timeshift_regs_1_real;
  assign io_timeshift_payload_0_1_imag = timeshift_regs_1_imag;
  assign io_distance_payload_1_1 = distance_regs_3;
  assign io_timeshift_payload_1_1_real = timeshift_regs_3_real;
  assign io_timeshift_payload_1_1_imag = timeshift_regs_3_imag;
  assign io_wave_valid = transfer_done;
  assign io_distance_valid = transfer_done;
  assign io_timeshift_valid = transfer_done;
  always @ (posedge clk) begin
    if(axi4_aw_valid)begin
      aw_area_awaddr_r <= axi4_aw_payload_addr;
      aw_area_awlen_r <= axi4_aw_payload_len;
      aw_area_awsize_r <= axi4_aw_payload_size;
      aw_area_awid_r <= axi4_aw_payload_id;
    end
    load_data_area_wvalid <= axi4_w_valid;
    if(load_data_area_wvalid)begin
      transfer_done <= (addr_hit ? wdata_r[0] : _zz_6[0]);
      wave_regs_3 <= _zz_8;
      wave_regs_0 <= _zz_12;
      wave_regs_2 <= _zz_16;
      wave_regs_1 <= _zz_20;
      distance_regs_0 <= _zz_24;
      distance_regs_1 <= _zz_28;
      distance_regs_2 <= _zz_32;
      distance_regs_3 <= _zz_36;
      timeshift_regs_3_imag <= _zz_40;
      timeshift_regs_2_real <= _zz_44;
      timeshift_regs_0_real <= _zz_48;
      timeshift_regs_3_real <= _zz_52;
      timeshift_regs_1_real <= _zz_56;
      timeshift_regs_0_imag <= _zz_60;
      timeshift_regs_2_imag <= _zz_64;
      timeshift_regs_1_imag <= _zz_68;
    end
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      load_data_area_current_addr <= 32'h0;
    end else begin
      if(load_data_area_wvalid)begin
        load_data_area_current_addr <= (load_data_area_current_addr + Axi4Incr_result);
      end
    end
  end

  always @ (posedge clk) begin
    wdata_r <= axi4_w_payload_data;
  end


endmodule
