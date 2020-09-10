// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : SFFT2d
// Git hash  : 5f24b07ffbfeb9934f5bea4487f4e9b5769de5ba



module SFFT2d (
  input               io_shit,
  input               io_line_in_valid,
  input      [17:0]   io_line_in_payload_real,
  input      [17:0]   io_line_in_payload_imag,
  output              io_line_out_valid,
  output     [17:0]   io_line_out_payload_0_real,
  output     [17:0]   io_line_out_payload_0_imag,
  output     [17:0]   io_line_out_payload_1_real,
  output     [17:0]   io_line_out_payload_1_imag,
  output     [17:0]   io_line_out_payload_2_real,
  output     [17:0]   io_line_out_payload_2_imag,
  output     [17:0]   io_line_out_payload_3_real,
  output     [17:0]   io_line_out_payload_3_imag,
  output              io_shiit,
  input               clk,
  input               reset
);
  wire                fFT2d_1_io_line_out_valid;
  wire       [17:0]   fFT2d_1_io_line_out_payload_0_real;
  wire       [17:0]   fFT2d_1_io_line_out_payload_0_imag;
  wire       [17:0]   fFT2d_1_io_line_out_payload_1_real;
  wire       [17:0]   fFT2d_1_io_line_out_payload_1_imag;
  wire       [17:0]   fFT2d_1_io_line_out_payload_2_real;
  wire       [17:0]   fFT2d_1_io_line_out_payload_2_imag;
  wire       [17:0]   fFT2d_1_io_line_out_payload_3_real;
  wire       [17:0]   fFT2d_1_io_line_out_payload_3_imag;
  wire       [0:0]    _zz_9;
  wire       [1:0]    _zz_10;
  wire       [17:0]   _zz_1;
  wire       [17:0]   _zz_2;
  reg        [17:0]   _zz_3;
  reg        [17:0]   _zz_4;
  reg        [17:0]   _zz_5;
  reg        [17:0]   _zz_6;
  reg        [17:0]   _zz_7;
  reg        [17:0]   _zz_8;
  reg                 count_up_inside_cnt_willIncrement;
  wire                count_up_inside_cnt_willClear;
  reg        [1:0]    count_up_inside_cnt_valueNext;
  reg        [1:0]    count_up_inside_cnt_value;
  wire                count_up_inside_cnt_willOverflowIfInc;
  wire                count_up_inside_cnt_willOverflow;

  assign _zz_9 = count_up_inside_cnt_willIncrement;
  assign _zz_10 = {1'd0, _zz_9};
  FFT2d fFT2d_1 (
    .io_line_in_valid              (count_up_inside_cnt_willOverflow          ), //i
    .io_line_in_payload_0_real     (_zz_7[17:0]                               ), //i
    .io_line_in_payload_0_imag     (_zz_8[17:0]                               ), //i
    .io_line_in_payload_1_real     (_zz_5[17:0]                               ), //i
    .io_line_in_payload_1_imag     (_zz_6[17:0]                               ), //i
    .io_line_in_payload_2_real     (_zz_3[17:0]                               ), //i
    .io_line_in_payload_2_imag     (_zz_4[17:0]                               ), //i
    .io_line_in_payload_3_real     (_zz_1[17:0]                               ), //i
    .io_line_in_payload_3_imag     (_zz_2[17:0]                               ), //i
    .io_line_out_valid             (fFT2d_1_io_line_out_valid                 ), //o
    .io_line_out_payload_0_real    (fFT2d_1_io_line_out_payload_0_real[17:0]  ), //o
    .io_line_out_payload_0_imag    (fFT2d_1_io_line_out_payload_0_imag[17:0]  ), //o
    .io_line_out_payload_1_real    (fFT2d_1_io_line_out_payload_1_real[17:0]  ), //o
    .io_line_out_payload_1_imag    (fFT2d_1_io_line_out_payload_1_imag[17:0]  ), //o
    .io_line_out_payload_2_real    (fFT2d_1_io_line_out_payload_2_real[17:0]  ), //o
    .io_line_out_payload_2_imag    (fFT2d_1_io_line_out_payload_2_imag[17:0]  ), //o
    .io_line_out_payload_3_real    (fFT2d_1_io_line_out_payload_3_real[17:0]  ), //o
    .io_line_out_payload_3_imag    (fFT2d_1_io_line_out_payload_3_imag[17:0]  ), //o
    .clk                           (clk                                       ), //i
    .reset                         (reset                                     )  //i
  );
  assign io_shiit = io_shit;
  assign _zz_1 = io_line_in_payload_real;
  assign _zz_2 = io_line_in_payload_imag;
  always @ (*) begin
    count_up_inside_cnt_willIncrement = 1'b0;
    if(io_line_in_valid)begin
      count_up_inside_cnt_willIncrement = 1'b1;
    end
  end

  assign count_up_inside_cnt_willClear = 1'b0;
  assign count_up_inside_cnt_willOverflowIfInc = (count_up_inside_cnt_value == 2'b11);
  assign count_up_inside_cnt_willOverflow = (count_up_inside_cnt_willOverflowIfInc && count_up_inside_cnt_willIncrement);
  always @ (*) begin
    count_up_inside_cnt_valueNext = (count_up_inside_cnt_value + _zz_10);
    if(count_up_inside_cnt_willClear)begin
      count_up_inside_cnt_valueNext = 2'b00;
    end
  end

  assign io_line_out_valid = fFT2d_1_io_line_out_valid;
  assign io_line_out_payload_0_real = fFT2d_1_io_line_out_payload_0_real;
  assign io_line_out_payload_0_imag = fFT2d_1_io_line_out_payload_0_imag;
  assign io_line_out_payload_1_real = fFT2d_1_io_line_out_payload_1_real;
  assign io_line_out_payload_1_imag = fFT2d_1_io_line_out_payload_1_imag;
  assign io_line_out_payload_2_real = fFT2d_1_io_line_out_payload_2_real;
  assign io_line_out_payload_2_imag = fFT2d_1_io_line_out_payload_2_imag;
  assign io_line_out_payload_3_real = fFT2d_1_io_line_out_payload_3_real;
  assign io_line_out_payload_3_imag = fFT2d_1_io_line_out_payload_3_imag;
  always @ (posedge clk) begin
    if(io_line_in_valid)begin
      _zz_3 <= _zz_1;
      _zz_4 <= _zz_2;
    end
    if(io_line_in_valid)begin
      _zz_5 <= _zz_3;
      _zz_6 <= _zz_4;
    end
    if(io_line_in_valid)begin
      _zz_7 <= _zz_5;
      _zz_8 <= _zz_6;
    end
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      count_up_inside_cnt_value <= 2'b00;
    end else begin
      count_up_inside_cnt_value <= count_up_inside_cnt_valueNext;
    end
  end


endmodule

module FFT2d (
  input               io_line_in_valid,
  input      [17:0]   io_line_in_payload_0_real,
  input      [17:0]   io_line_in_payload_0_imag,
  input      [17:0]   io_line_in_payload_1_real,
  input      [17:0]   io_line_in_payload_1_imag,
  input      [17:0]   io_line_in_payload_2_real,
  input      [17:0]   io_line_in_payload_2_imag,
  input      [17:0]   io_line_in_payload_3_real,
  input      [17:0]   io_line_in_payload_3_imag,
  output              io_line_out_valid,
  output     [17:0]   io_line_out_payload_0_real,
  output     [17:0]   io_line_out_payload_0_imag,
  output     [17:0]   io_line_out_payload_1_real,
  output     [17:0]   io_line_out_payload_1_imag,
  output     [17:0]   io_line_out_payload_2_real,
  output     [17:0]   io_line_out_payload_2_imag,
  output     [17:0]   io_line_out_payload_3_real,
  output     [17:0]   io_line_out_payload_3_imag,
  input               clk,
  input               reset
);
  reg        [17:0]   _zz_14;
  reg        [17:0]   _zz_15;
  reg        [17:0]   _zz_16;
  reg        [17:0]   _zz_17;
  reg        [17:0]   _zz_18;
  reg        [17:0]   _zz_19;
  reg        [17:0]   _zz_20;
  reg        [17:0]   _zz_21;
  wire                myFFT_2_fft_row_valid;
  wire       [17:0]   myFFT_2_fft_row_payload_0_real;
  wire       [17:0]   myFFT_2_fft_row_payload_0_imag;
  wire       [17:0]   myFFT_2_fft_row_payload_1_real;
  wire       [17:0]   myFFT_2_fft_row_payload_1_imag;
  wire       [17:0]   myFFT_2_fft_row_payload_2_real;
  wire       [17:0]   myFFT_2_fft_row_payload_2_imag;
  wire       [17:0]   myFFT_2_fft_row_payload_3_real;
  wire       [17:0]   myFFT_2_fft_row_payload_3_imag;
  wire                myFFT_3_fft_col_out_valid;
  wire       [17:0]   myFFT_3_fft_col_out_payload_0_real;
  wire       [17:0]   myFFT_3_fft_col_out_payload_0_imag;
  wire       [17:0]   myFFT_3_fft_col_out_payload_1_real;
  wire       [17:0]   myFFT_3_fft_col_out_payload_1_imag;
  wire       [17:0]   myFFT_3_fft_col_out_payload_2_real;
  wire       [17:0]   myFFT_3_fft_col_out_payload_2_imag;
  wire       [17:0]   myFFT_3_fft_col_out_payload_3_real;
  wire       [17:0]   myFFT_3_fft_col_out_payload_3_imag;
  wire       [0:0]    _zz_22;
  wire       [1:0]    _zz_23;
  wire       [0:0]    _zz_24;
  wire       [1:0]    _zz_25;
  reg        [17:0]   img_reg_array_0_0_real;
  reg        [17:0]   img_reg_array_0_0_imag;
  reg        [17:0]   img_reg_array_0_1_real;
  reg        [17:0]   img_reg_array_0_1_imag;
  reg        [17:0]   img_reg_array_0_2_real;
  reg        [17:0]   img_reg_array_0_2_imag;
  reg        [17:0]   img_reg_array_0_3_real;
  reg        [17:0]   img_reg_array_0_3_imag;
  reg        [17:0]   img_reg_array_1_0_real;
  reg        [17:0]   img_reg_array_1_0_imag;
  reg        [17:0]   img_reg_array_1_1_real;
  reg        [17:0]   img_reg_array_1_1_imag;
  reg        [17:0]   img_reg_array_1_2_real;
  reg        [17:0]   img_reg_array_1_2_imag;
  reg        [17:0]   img_reg_array_1_3_real;
  reg        [17:0]   img_reg_array_1_3_imag;
  reg        [17:0]   img_reg_array_2_0_real;
  reg        [17:0]   img_reg_array_2_0_imag;
  reg        [17:0]   img_reg_array_2_1_real;
  reg        [17:0]   img_reg_array_2_1_imag;
  reg        [17:0]   img_reg_array_2_2_real;
  reg        [17:0]   img_reg_array_2_2_imag;
  reg        [17:0]   img_reg_array_2_3_real;
  reg        [17:0]   img_reg_array_2_3_imag;
  reg        [17:0]   img_reg_array_3_0_real;
  reg        [17:0]   img_reg_array_3_0_imag;
  reg        [17:0]   img_reg_array_3_1_real;
  reg        [17:0]   img_reg_array_3_1_imag;
  reg        [17:0]   img_reg_array_3_2_real;
  reg        [17:0]   img_reg_array_3_2_imag;
  reg        [17:0]   img_reg_array_3_3_real;
  reg        [17:0]   img_reg_array_3_3_imag;
  reg                 row_addr_willIncrement;
  wire                row_addr_willClear;
  reg        [1:0]    row_addr_valueNext;
  reg        [1:0]    row_addr_value;
  wire                row_addr_willOverflowIfInc;
  wire                row_addr_willOverflow;
  wire       [3:0]    _zz_1;
  wire                _zz_2;
  wire                _zz_3;
  wire                _zz_4;
  wire                _zz_5;
  wire       [17:0]   _zz_6;
  wire       [17:0]   _zz_7;
  wire       [17:0]   _zz_8;
  wire       [17:0]   _zz_9;
  wire       [17:0]   _zz_10;
  wire       [17:0]   _zz_11;
  wire       [17:0]   _zz_12;
  wire       [17:0]   _zz_13;
  reg                 null_cnt_willIncrement;
  wire                null_cnt_willClear;
  reg        [1:0]    null_cnt_valueNext;
  reg        [1:0]    null_cnt_value;
  wire                null_cnt_willOverflowIfInc;
  wire                null_cnt_willOverflow;
  reg                 null_cond_period_minus_1;
  wire                null_cond_period;
  reg        [1:0]    col_addr;
  wire                fft_col_in_valid;
  wire       [17:0]   fft_col_in_payload_0_real;
  wire       [17:0]   fft_col_in_payload_0_imag;
  wire       [17:0]   fft_col_in_payload_1_real;
  wire       [17:0]   fft_col_in_payload_1_imag;
  wire       [17:0]   fft_col_in_payload_2_real;
  wire       [17:0]   fft_col_in_payload_2_imag;
  wire       [17:0]   fft_col_in_payload_3_real;
  wire       [17:0]   fft_col_in_payload_3_imag;
  reg                 null_cond_period_regNext;
  reg                 myFFT_3_fft_col_out_regNext_valid;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_0_real;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_0_imag;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_1_real;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_1_imag;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_2_real;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_2_imag;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_3_real;
  reg        [17:0]   myFFT_3_fft_col_out_regNext_payload_3_imag;

  assign _zz_22 = row_addr_willIncrement;
  assign _zz_23 = {1'd0, _zz_22};
  assign _zz_24 = null_cnt_willIncrement;
  assign _zz_25 = {1'd0, _zz_24};
  MyFFT myFFT_2 (
    .io_data_in_valid             (io_line_in_valid                      ), //i
    .io_data_in_payload_0_real    (io_line_in_payload_0_real[17:0]       ), //i
    .io_data_in_payload_0_imag    (io_line_in_payload_0_imag[17:0]       ), //i
    .io_data_in_payload_1_real    (io_line_in_payload_1_real[17:0]       ), //i
    .io_data_in_payload_1_imag    (io_line_in_payload_1_imag[17:0]       ), //i
    .io_data_in_payload_2_real    (io_line_in_payload_2_real[17:0]       ), //i
    .io_data_in_payload_2_imag    (io_line_in_payload_2_imag[17:0]       ), //i
    .io_data_in_payload_3_real    (io_line_in_payload_3_real[17:0]       ), //i
    .io_data_in_payload_3_imag    (io_line_in_payload_3_imag[17:0]       ), //i
    .fft_row_valid                (myFFT_2_fft_row_valid                 ), //o
    .fft_row_payload_0_real       (myFFT_2_fft_row_payload_0_real[17:0]  ), //o
    .fft_row_payload_0_imag       (myFFT_2_fft_row_payload_0_imag[17:0]  ), //o
    .fft_row_payload_1_real       (myFFT_2_fft_row_payload_1_real[17:0]  ), //o
    .fft_row_payload_1_imag       (myFFT_2_fft_row_payload_1_imag[17:0]  ), //o
    .fft_row_payload_2_real       (myFFT_2_fft_row_payload_2_real[17:0]  ), //o
    .fft_row_payload_2_imag       (myFFT_2_fft_row_payload_2_imag[17:0]  ), //o
    .fft_row_payload_3_real       (myFFT_2_fft_row_payload_3_real[17:0]  ), //o
    .fft_row_payload_3_imag       (myFFT_2_fft_row_payload_3_imag[17:0]  ), //o
    .clk                          (clk                                   ), //i
    .reset                        (reset                                 )  //i
  );
  MyFFT_1 myFFT_3 (
    .io_data_in_valid              (fft_col_in_valid                          ), //i
    .io_data_in_payload_0_real     (fft_col_in_payload_0_real[17:0]           ), //i
    .io_data_in_payload_0_imag     (fft_col_in_payload_0_imag[17:0]           ), //i
    .io_data_in_payload_1_real     (fft_col_in_payload_1_real[17:0]           ), //i
    .io_data_in_payload_1_imag     (fft_col_in_payload_1_imag[17:0]           ), //i
    .io_data_in_payload_2_real     (fft_col_in_payload_2_real[17:0]           ), //i
    .io_data_in_payload_2_imag     (fft_col_in_payload_2_imag[17:0]           ), //i
    .io_data_in_payload_3_real     (fft_col_in_payload_3_real[17:0]           ), //i
    .io_data_in_payload_3_imag     (fft_col_in_payload_3_imag[17:0]           ), //i
    .fft_col_out_valid             (myFFT_3_fft_col_out_valid                 ), //o
    .fft_col_out_payload_0_real    (myFFT_3_fft_col_out_payload_0_real[17:0]  ), //o
    .fft_col_out_payload_0_imag    (myFFT_3_fft_col_out_payload_0_imag[17:0]  ), //o
    .fft_col_out_payload_1_real    (myFFT_3_fft_col_out_payload_1_real[17:0]  ), //o
    .fft_col_out_payload_1_imag    (myFFT_3_fft_col_out_payload_1_imag[17:0]  ), //o
    .fft_col_out_payload_2_real    (myFFT_3_fft_col_out_payload_2_real[17:0]  ), //o
    .fft_col_out_payload_2_imag    (myFFT_3_fft_col_out_payload_2_imag[17:0]  ), //o
    .fft_col_out_payload_3_real    (myFFT_3_fft_col_out_payload_3_real[17:0]  ), //o
    .fft_col_out_payload_3_imag    (myFFT_3_fft_col_out_payload_3_imag[17:0]  ), //o
    .clk                           (clk                                       ), //i
    .reset                         (reset                                     )  //i
  );
  always @(*) begin
    case(col_addr)
      2'b00 : begin
        _zz_14 = img_reg_array_0_0_real;
        _zz_15 = img_reg_array_0_0_imag;
        _zz_16 = img_reg_array_1_0_real;
        _zz_17 = img_reg_array_1_0_imag;
        _zz_18 = img_reg_array_2_0_real;
        _zz_19 = img_reg_array_2_0_imag;
        _zz_20 = img_reg_array_3_0_real;
        _zz_21 = img_reg_array_3_0_imag;
      end
      2'b01 : begin
        _zz_14 = img_reg_array_0_1_real;
        _zz_15 = img_reg_array_0_1_imag;
        _zz_16 = img_reg_array_1_1_real;
        _zz_17 = img_reg_array_1_1_imag;
        _zz_18 = img_reg_array_2_1_real;
        _zz_19 = img_reg_array_2_1_imag;
        _zz_20 = img_reg_array_3_1_real;
        _zz_21 = img_reg_array_3_1_imag;
      end
      2'b10 : begin
        _zz_14 = img_reg_array_0_2_real;
        _zz_15 = img_reg_array_0_2_imag;
        _zz_16 = img_reg_array_1_2_real;
        _zz_17 = img_reg_array_1_2_imag;
        _zz_18 = img_reg_array_2_2_real;
        _zz_19 = img_reg_array_2_2_imag;
        _zz_20 = img_reg_array_3_2_real;
        _zz_21 = img_reg_array_3_2_imag;
      end
      default : begin
        _zz_14 = img_reg_array_0_3_real;
        _zz_15 = img_reg_array_0_3_imag;
        _zz_16 = img_reg_array_1_3_real;
        _zz_17 = img_reg_array_1_3_imag;
        _zz_18 = img_reg_array_2_3_real;
        _zz_19 = img_reg_array_2_3_imag;
        _zz_20 = img_reg_array_3_3_real;
        _zz_21 = img_reg_array_3_3_imag;
      end
    endcase
  end

  always @ (*) begin
    row_addr_willIncrement = 1'b0;
    if(myFFT_2_fft_row_valid)begin
      row_addr_willIncrement = 1'b1;
    end
  end

  assign row_addr_willClear = 1'b0;
  assign row_addr_willOverflowIfInc = (row_addr_value == 2'b11);
  assign row_addr_willOverflow = (row_addr_willOverflowIfInc && row_addr_willIncrement);
  always @ (*) begin
    row_addr_valueNext = (row_addr_value + _zz_23);
    if(row_addr_willClear)begin
      row_addr_valueNext = 2'b00;
    end
  end

  assign _zz_1 = ({3'd0,1'b1} <<< row_addr_value);
  assign _zz_2 = _zz_1[0];
  assign _zz_3 = _zz_1[1];
  assign _zz_4 = _zz_1[2];
  assign _zz_5 = _zz_1[3];
  assign _zz_6 = myFFT_2_fft_row_payload_0_real;
  assign _zz_7 = myFFT_2_fft_row_payload_0_imag;
  assign _zz_8 = myFFT_2_fft_row_payload_1_real;
  assign _zz_9 = myFFT_2_fft_row_payload_1_imag;
  assign _zz_10 = myFFT_2_fft_row_payload_2_real;
  assign _zz_11 = myFFT_2_fft_row_payload_2_imag;
  assign _zz_12 = myFFT_2_fft_row_payload_3_real;
  assign _zz_13 = myFFT_2_fft_row_payload_3_imag;
  always @ (*) begin
    null_cnt_willIncrement = 1'b0;
    if(null_cond_period)begin
      null_cnt_willIncrement = 1'b1;
    end
  end

  assign null_cnt_willClear = 1'b0;
  assign null_cnt_willOverflowIfInc = (null_cnt_value == 2'b11);
  assign null_cnt_willOverflow = (null_cnt_willOverflowIfInc && null_cnt_willIncrement);
  always @ (*) begin
    null_cnt_valueNext = (null_cnt_value + _zz_25);
    if(null_cnt_willClear)begin
      null_cnt_valueNext = 2'b00;
    end
  end

  assign null_cond_period = (row_addr_willOverflow || null_cond_period_minus_1);
  assign fft_col_in_payload_0_real = _zz_14;
  assign fft_col_in_payload_0_imag = _zz_15;
  assign fft_col_in_payload_1_real = _zz_16;
  assign fft_col_in_payload_1_imag = _zz_17;
  assign fft_col_in_payload_2_real = _zz_18;
  assign fft_col_in_payload_2_imag = _zz_19;
  assign fft_col_in_payload_3_real = _zz_20;
  assign fft_col_in_payload_3_imag = _zz_21;
  assign fft_col_in_valid = null_cond_period_regNext;
  assign io_line_out_valid = myFFT_3_fft_col_out_regNext_valid;
  assign io_line_out_payload_0_real = myFFT_3_fft_col_out_regNext_payload_0_real;
  assign io_line_out_payload_0_imag = myFFT_3_fft_col_out_regNext_payload_0_imag;
  assign io_line_out_payload_1_real = myFFT_3_fft_col_out_regNext_payload_1_real;
  assign io_line_out_payload_1_imag = myFFT_3_fft_col_out_regNext_payload_1_imag;
  assign io_line_out_payload_2_real = myFFT_3_fft_col_out_regNext_payload_2_real;
  assign io_line_out_payload_2_imag = myFFT_3_fft_col_out_regNext_payload_2_imag;
  assign io_line_out_payload_3_real = myFFT_3_fft_col_out_regNext_payload_3_real;
  assign io_line_out_payload_3_imag = myFFT_3_fft_col_out_regNext_payload_3_imag;
  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      row_addr_value <= 2'b00;
      null_cnt_value <= 2'b00;
      null_cond_period_minus_1 <= 1'b0;
      null_cond_period_regNext <= 1'b0;
      myFFT_3_fft_col_out_regNext_valid <= 1'b0;
    end else begin
      row_addr_value <= row_addr_valueNext;
      null_cnt_value <= null_cnt_valueNext;
      if(row_addr_willOverflow)begin
        null_cond_period_minus_1 <= 1'b1;
      end else begin
        if(null_cnt_willOverflow)begin
          null_cond_period_minus_1 <= 1'b0;
        end
      end
      null_cond_period_regNext <= null_cond_period;
      myFFT_3_fft_col_out_regNext_valid <= myFFT_3_fft_col_out_valid;
    end
  end

  always @ (posedge clk) begin
    if(myFFT_2_fft_row_valid)begin
      if(_zz_2)begin
        img_reg_array_0_0_real <= _zz_6;
      end
      if(_zz_3)begin
        img_reg_array_1_0_real <= _zz_6;
      end
      if(_zz_4)begin
        img_reg_array_2_0_real <= _zz_6;
      end
      if(_zz_5)begin
        img_reg_array_3_0_real <= _zz_6;
      end
      if(_zz_2)begin
        img_reg_array_0_0_imag <= _zz_7;
      end
      if(_zz_3)begin
        img_reg_array_1_0_imag <= _zz_7;
      end
      if(_zz_4)begin
        img_reg_array_2_0_imag <= _zz_7;
      end
      if(_zz_5)begin
        img_reg_array_3_0_imag <= _zz_7;
      end
      if(_zz_2)begin
        img_reg_array_0_1_real <= _zz_8;
      end
      if(_zz_3)begin
        img_reg_array_1_1_real <= _zz_8;
      end
      if(_zz_4)begin
        img_reg_array_2_1_real <= _zz_8;
      end
      if(_zz_5)begin
        img_reg_array_3_1_real <= _zz_8;
      end
      if(_zz_2)begin
        img_reg_array_0_1_imag <= _zz_9;
      end
      if(_zz_3)begin
        img_reg_array_1_1_imag <= _zz_9;
      end
      if(_zz_4)begin
        img_reg_array_2_1_imag <= _zz_9;
      end
      if(_zz_5)begin
        img_reg_array_3_1_imag <= _zz_9;
      end
      if(_zz_2)begin
        img_reg_array_0_2_real <= _zz_10;
      end
      if(_zz_3)begin
        img_reg_array_1_2_real <= _zz_10;
      end
      if(_zz_4)begin
        img_reg_array_2_2_real <= _zz_10;
      end
      if(_zz_5)begin
        img_reg_array_3_2_real <= _zz_10;
      end
      if(_zz_2)begin
        img_reg_array_0_2_imag <= _zz_11;
      end
      if(_zz_3)begin
        img_reg_array_1_2_imag <= _zz_11;
      end
      if(_zz_4)begin
        img_reg_array_2_2_imag <= _zz_11;
      end
      if(_zz_5)begin
        img_reg_array_3_2_imag <= _zz_11;
      end
      if(_zz_2)begin
        img_reg_array_0_3_real <= _zz_12;
      end
      if(_zz_3)begin
        img_reg_array_1_3_real <= _zz_12;
      end
      if(_zz_4)begin
        img_reg_array_2_3_real <= _zz_12;
      end
      if(_zz_5)begin
        img_reg_array_3_3_real <= _zz_12;
      end
      if(_zz_2)begin
        img_reg_array_0_3_imag <= _zz_13;
      end
      if(_zz_3)begin
        img_reg_array_1_3_imag <= _zz_13;
      end
      if(_zz_4)begin
        img_reg_array_2_3_imag <= _zz_13;
      end
      if(_zz_5)begin
        img_reg_array_3_3_imag <= _zz_13;
      end
    end
    col_addr <= null_cnt_value;
    myFFT_3_fft_col_out_regNext_payload_0_real <= myFFT_3_fft_col_out_payload_0_real;
    myFFT_3_fft_col_out_regNext_payload_0_imag <= myFFT_3_fft_col_out_payload_0_imag;
    myFFT_3_fft_col_out_regNext_payload_1_real <= myFFT_3_fft_col_out_payload_1_real;
    myFFT_3_fft_col_out_regNext_payload_1_imag <= myFFT_3_fft_col_out_payload_1_imag;
    myFFT_3_fft_col_out_regNext_payload_2_real <= myFFT_3_fft_col_out_payload_2_real;
    myFFT_3_fft_col_out_regNext_payload_2_imag <= myFFT_3_fft_col_out_payload_2_imag;
    myFFT_3_fft_col_out_regNext_payload_3_real <= myFFT_3_fft_col_out_payload_3_real;
    myFFT_3_fft_col_out_regNext_payload_3_imag <= myFFT_3_fft_col_out_payload_3_imag;
  end


endmodule

module MyFFT_1 (
  input               io_data_in_valid,
  input      [17:0]   io_data_in_payload_0_real,
  input      [17:0]   io_data_in_payload_0_imag,
  input      [17:0]   io_data_in_payload_1_real,
  input      [17:0]   io_data_in_payload_1_imag,
  input      [17:0]   io_data_in_payload_2_real,
  input      [17:0]   io_data_in_payload_2_imag,
  input      [17:0]   io_data_in_payload_3_real,
  input      [17:0]   io_data_in_payload_3_imag,
  output              fft_col_out_valid,
  output     [17:0]   fft_col_out_payload_0_real,
  output     [17:0]   fft_col_out_payload_0_imag,
  output     [17:0]   fft_col_out_payload_1_real,
  output     [17:0]   fft_col_out_payload_1_imag,
  output     [17:0]   fft_col_out_payload_2_real,
  output     [17:0]   fft_col_out_payload_2_imag,
  output     [17:0]   fft_col_out_payload_3_real,
  output     [17:0]   fft_col_out_payload_3_imag,
  input               clk,
  input               reset
);
  wire       [35:0]   _zz_21;
  wire       [35:0]   _zz_22;
  wire       [35:0]   _zz_23;
  wire       [35:0]   _zz_24;
  wire       [35:0]   _zz_25;
  wire       [35:0]   _zz_26;
  wire       [35:0]   _zz_27;
  wire       [35:0]   _zz_28;
  wire       [35:0]   _zz_29;
  wire       [35:0]   _zz_30;
  wire       [35:0]   _zz_31;
  wire       [35:0]   _zz_32;
  wire       [35:0]   _zz_33;
  wire       [35:0]   _zz_34;
  wire       [35:0]   _zz_35;
  wire       [35:0]   _zz_36;
  wire       [35:0]   _zz_37;
  wire       [35:0]   _zz_38;
  wire       [35:0]   _zz_39;
  wire       [35:0]   _zz_40;
  wire       [35:0]   _zz_41;
  wire       [35:0]   _zz_42;
  wire       [35:0]   _zz_43;
  wire       [35:0]   _zz_44;
  wire       [35:0]   fixTo_dout;
  wire       [35:0]   fixTo_1_dout;
  wire       [17:0]   fixTo_2_dout;
  wire       [17:0]   fixTo_3_dout;
  wire       [17:0]   fixTo_4_dout;
  wire       [17:0]   fixTo_5_dout;
  wire       [35:0]   fixTo_6_dout;
  wire       [35:0]   fixTo_7_dout;
  wire       [17:0]   fixTo_8_dout;
  wire       [17:0]   fixTo_9_dout;
  wire       [17:0]   fixTo_10_dout;
  wire       [17:0]   fixTo_11_dout;
  wire       [35:0]   fixTo_12_dout;
  wire       [35:0]   fixTo_13_dout;
  wire       [17:0]   fixTo_14_dout;
  wire       [17:0]   fixTo_15_dout;
  wire       [17:0]   fixTo_16_dout;
  wire       [17:0]   fixTo_17_dout;
  wire       [35:0]   fixTo_18_dout;
  wire       [35:0]   fixTo_19_dout;
  wire       [17:0]   fixTo_20_dout;
  wire       [17:0]   fixTo_21_dout;
  wire       [17:0]   fixTo_22_dout;
  wire       [17:0]   fixTo_23_dout;
  wire       [17:0]   _zz_45;
  wire       [35:0]   _zz_46;
  wire       [35:0]   _zz_47;
  wire       [17:0]   _zz_48;
  wire       [35:0]   _zz_49;
  wire       [35:0]   _zz_50;
  wire       [35:0]   _zz_51;
  wire       [17:0]   _zz_52;
  wire       [35:0]   _zz_53;
  wire       [35:0]   _zz_54;
  wire       [35:0]   _zz_55;
  wire       [35:0]   _zz_56;
  wire       [35:0]   _zz_57;
  wire       [35:0]   _zz_58;
  wire       [26:0]   _zz_59;
  wire       [35:0]   _zz_60;
  wire       [17:0]   _zz_61;
  wire       [35:0]   _zz_62;
  wire       [35:0]   _zz_63;
  wire       [35:0]   _zz_64;
  wire       [35:0]   _zz_65;
  wire       [35:0]   _zz_66;
  wire       [26:0]   _zz_67;
  wire       [35:0]   _zz_68;
  wire       [17:0]   _zz_69;
  wire       [35:0]   _zz_70;
  wire       [35:0]   _zz_71;
  wire       [35:0]   _zz_72;
  wire       [35:0]   _zz_73;
  wire       [35:0]   _zz_74;
  wire       [26:0]   _zz_75;
  wire       [35:0]   _zz_76;
  wire       [17:0]   _zz_77;
  wire       [35:0]   _zz_78;
  wire       [35:0]   _zz_79;
  wire       [35:0]   _zz_80;
  wire       [35:0]   _zz_81;
  wire       [35:0]   _zz_82;
  wire       [26:0]   _zz_83;
  wire       [35:0]   _zz_84;
  wire       [17:0]   _zz_85;
  wire       [17:0]   _zz_86;
  wire       [35:0]   _zz_87;
  wire       [35:0]   _zz_88;
  wire       [17:0]   _zz_89;
  wire       [35:0]   _zz_90;
  wire       [35:0]   _zz_91;
  wire       [35:0]   _zz_92;
  wire       [17:0]   _zz_93;
  wire       [35:0]   _zz_94;
  wire       [35:0]   _zz_95;
  wire       [35:0]   _zz_96;
  wire       [35:0]   _zz_97;
  wire       [35:0]   _zz_98;
  wire       [35:0]   _zz_99;
  wire       [26:0]   _zz_100;
  wire       [35:0]   _zz_101;
  wire       [17:0]   _zz_102;
  wire       [35:0]   _zz_103;
  wire       [35:0]   _zz_104;
  wire       [35:0]   _zz_105;
  wire       [35:0]   _zz_106;
  wire       [35:0]   _zz_107;
  wire       [26:0]   _zz_108;
  wire       [35:0]   _zz_109;
  wire       [17:0]   _zz_110;
  wire       [35:0]   _zz_111;
  wire       [35:0]   _zz_112;
  wire       [35:0]   _zz_113;
  wire       [35:0]   _zz_114;
  wire       [35:0]   _zz_115;
  wire       [26:0]   _zz_116;
  wire       [35:0]   _zz_117;
  wire       [17:0]   _zz_118;
  wire       [35:0]   _zz_119;
  wire       [35:0]   _zz_120;
  wire       [35:0]   _zz_121;
  wire       [35:0]   _zz_122;
  wire       [35:0]   _zz_123;
  wire       [26:0]   _zz_124;
  wire       [35:0]   _zz_125;
  wire       [17:0]   _zz_126;
  wire       [17:0]   _zz_127;
  wire       [35:0]   _zz_128;
  wire       [35:0]   _zz_129;
  wire       [17:0]   _zz_130;
  wire       [35:0]   _zz_131;
  wire       [35:0]   _zz_132;
  wire       [35:0]   _zz_133;
  wire       [17:0]   _zz_134;
  wire       [35:0]   _zz_135;
  wire       [35:0]   _zz_136;
  wire       [35:0]   _zz_137;
  wire       [35:0]   _zz_138;
  wire       [35:0]   _zz_139;
  wire       [35:0]   _zz_140;
  wire       [26:0]   _zz_141;
  wire       [35:0]   _zz_142;
  wire       [17:0]   _zz_143;
  wire       [35:0]   _zz_144;
  wire       [35:0]   _zz_145;
  wire       [35:0]   _zz_146;
  wire       [35:0]   _zz_147;
  wire       [35:0]   _zz_148;
  wire       [26:0]   _zz_149;
  wire       [35:0]   _zz_150;
  wire       [17:0]   _zz_151;
  wire       [35:0]   _zz_152;
  wire       [35:0]   _zz_153;
  wire       [35:0]   _zz_154;
  wire       [35:0]   _zz_155;
  wire       [35:0]   _zz_156;
  wire       [26:0]   _zz_157;
  wire       [35:0]   _zz_158;
  wire       [17:0]   _zz_159;
  wire       [35:0]   _zz_160;
  wire       [35:0]   _zz_161;
  wire       [35:0]   _zz_162;
  wire       [35:0]   _zz_163;
  wire       [35:0]   _zz_164;
  wire       [26:0]   _zz_165;
  wire       [35:0]   _zz_166;
  wire       [17:0]   _zz_167;
  wire       [17:0]   _zz_168;
  wire       [35:0]   _zz_169;
  wire       [35:0]   _zz_170;
  wire       [17:0]   _zz_171;
  wire       [35:0]   _zz_172;
  wire       [35:0]   _zz_173;
  wire       [35:0]   _zz_174;
  wire       [17:0]   _zz_175;
  wire       [35:0]   _zz_176;
  wire       [35:0]   _zz_177;
  wire       [35:0]   _zz_178;
  wire       [35:0]   _zz_179;
  wire       [35:0]   _zz_180;
  wire       [35:0]   _zz_181;
  wire       [26:0]   _zz_182;
  wire       [35:0]   _zz_183;
  wire       [17:0]   _zz_184;
  wire       [35:0]   _zz_185;
  wire       [35:0]   _zz_186;
  wire       [35:0]   _zz_187;
  wire       [35:0]   _zz_188;
  wire       [35:0]   _zz_189;
  wire       [26:0]   _zz_190;
  wire       [35:0]   _zz_191;
  wire       [17:0]   _zz_192;
  wire       [35:0]   _zz_193;
  wire       [35:0]   _zz_194;
  wire       [35:0]   _zz_195;
  wire       [35:0]   _zz_196;
  wire       [35:0]   _zz_197;
  wire       [26:0]   _zz_198;
  wire       [35:0]   _zz_199;
  wire       [17:0]   _zz_200;
  wire       [35:0]   _zz_201;
  wire       [35:0]   _zz_202;
  wire       [35:0]   _zz_203;
  wire       [35:0]   _zz_204;
  wire       [35:0]   _zz_205;
  wire       [26:0]   _zz_206;
  wire       [35:0]   _zz_207;
  wire       [17:0]   _zz_208;
  reg        [17:0]   data_in_0_real;
  reg        [17:0]   data_in_0_imag;
  reg        [17:0]   data_in_1_real;
  reg        [17:0]   data_in_1_imag;
  reg        [17:0]   data_in_2_real;
  reg        [17:0]   data_in_2_imag;
  reg        [17:0]   data_in_3_real;
  reg        [17:0]   data_in_3_imag;
  wire       [17:0]   twiddle_factor_table_0_real;
  wire       [17:0]   twiddle_factor_table_0_imag;
  wire       [17:0]   twiddle_factor_table_1_real;
  wire       [17:0]   twiddle_factor_table_1_imag;
  wire       [17:0]   twiddle_factor_table_2_real;
  wire       [17:0]   twiddle_factor_table_2_imag;
  wire       [17:0]   data_reorder_0_real;
  wire       [17:0]   data_reorder_0_imag;
  wire       [17:0]   data_reorder_1_real;
  wire       [17:0]   data_reorder_1_imag;
  wire       [17:0]   data_reorder_2_real;
  wire       [17:0]   data_reorder_2_imag;
  wire       [17:0]   data_reorder_3_real;
  wire       [17:0]   data_reorder_3_imag;
  reg        [17:0]   data_mid_0_0_real;
  reg        [17:0]   data_mid_0_0_imag;
  reg        [17:0]   data_mid_0_1_real;
  reg        [17:0]   data_mid_0_1_imag;
  reg        [17:0]   data_mid_0_2_real;
  reg        [17:0]   data_mid_0_2_imag;
  reg        [17:0]   data_mid_0_3_real;
  reg        [17:0]   data_mid_0_3_imag;
  reg        [17:0]   data_mid_1_0_real;
  reg        [17:0]   data_mid_1_0_imag;
  reg        [17:0]   data_mid_1_1_real;
  reg        [17:0]   data_mid_1_1_imag;
  reg        [17:0]   data_mid_1_2_real;
  reg        [17:0]   data_mid_1_2_imag;
  reg        [17:0]   data_mid_1_3_real;
  reg        [17:0]   data_mid_1_3_imag;
  reg        [17:0]   data_mid_2_0_real;
  reg        [17:0]   data_mid_2_0_imag;
  reg        [17:0]   data_mid_2_1_real;
  reg        [17:0]   data_mid_2_1_imag;
  reg        [17:0]   data_mid_2_2_real;
  reg        [17:0]   data_mid_2_2_imag;
  reg        [17:0]   data_mid_2_3_real;
  reg        [17:0]   data_mid_2_3_imag;
  wire       [35:0]   _zz_1;
  wire       [35:0]   _zz_2;
  wire       [35:0]   _zz_3;
  wire       [0:0]    _zz_4;
  wire       [0:0]    _zz_5;
  wire       [35:0]   _zz_6;
  wire       [35:0]   _zz_7;
  wire       [35:0]   _zz_8;
  wire       [0:0]    _zz_9;
  wire       [0:0]    _zz_10;
  wire       [35:0]   _zz_11;
  wire       [35:0]   _zz_12;
  wire       [35:0]   _zz_13;
  wire       [0:0]    _zz_14;
  wire       [0:0]    _zz_15;
  wire       [35:0]   _zz_16;
  wire       [35:0]   _zz_17;
  wire       [35:0]   _zz_18;
  wire       [0:0]    _zz_19;
  wire       [0:0]    _zz_20;
  reg                 io_data_in_valid_delay_1;
  reg                 io_data_in_valid_delay_2;
  reg                 io_data_in_valid_delay_3;
  reg                 io_data_in_valid_delay_4;

  assign _zz_45 = ($signed(twiddle_factor_table_0_real) + $signed(twiddle_factor_table_0_imag));
  assign _zz_46 = ($signed(_zz_3) - $signed(_zz_47));
  assign _zz_47 = ($signed(_zz_48) * $signed(twiddle_factor_table_0_imag));
  assign _zz_48 = ($signed(data_mid_0_1_real) + $signed(data_mid_0_1_imag));
  assign _zz_49 = fixTo_dout;
  assign _zz_50 = ($signed(_zz_3) + $signed(_zz_51));
  assign _zz_51 = ($signed(_zz_52) * $signed(twiddle_factor_table_0_real));
  assign _zz_52 = ($signed(data_mid_0_1_imag) - $signed(data_mid_0_1_real));
  assign _zz_53 = fixTo_1_dout;
  assign _zz_54 = _zz_55[35 : 0];
  assign _zz_55 = _zz_56;
  assign _zz_56 = ($signed(_zz_57) >>> _zz_4);
  assign _zz_57 = _zz_58;
  assign _zz_58 = ($signed(_zz_60) - $signed(_zz_1));
  assign _zz_59 = ({9'd0,data_mid_0_0_real} <<< 9);
  assign _zz_60 = {{9{_zz_59[26]}}, _zz_59};
  assign _zz_61 = fixTo_2_dout;
  assign _zz_62 = _zz_63[35 : 0];
  assign _zz_63 = _zz_64;
  assign _zz_64 = ($signed(_zz_65) >>> _zz_4);
  assign _zz_65 = _zz_66;
  assign _zz_66 = ($signed(_zz_68) - $signed(_zz_2));
  assign _zz_67 = ({9'd0,data_mid_0_0_imag} <<< 9);
  assign _zz_68 = {{9{_zz_67[26]}}, _zz_67};
  assign _zz_69 = fixTo_3_dout;
  assign _zz_70 = _zz_71[35 : 0];
  assign _zz_71 = _zz_72;
  assign _zz_72 = ($signed(_zz_73) >>> _zz_5);
  assign _zz_73 = _zz_74;
  assign _zz_74 = ($signed(_zz_76) + $signed(_zz_1));
  assign _zz_75 = ({9'd0,data_mid_0_0_real} <<< 9);
  assign _zz_76 = {{9{_zz_75[26]}}, _zz_75};
  assign _zz_77 = fixTo_4_dout;
  assign _zz_78 = _zz_79[35 : 0];
  assign _zz_79 = _zz_80;
  assign _zz_80 = ($signed(_zz_81) >>> _zz_5);
  assign _zz_81 = _zz_82;
  assign _zz_82 = ($signed(_zz_84) + $signed(_zz_2));
  assign _zz_83 = ({9'd0,data_mid_0_0_imag} <<< 9);
  assign _zz_84 = {{9{_zz_83[26]}}, _zz_83};
  assign _zz_85 = fixTo_5_dout;
  assign _zz_86 = ($signed(twiddle_factor_table_0_real) + $signed(twiddle_factor_table_0_imag));
  assign _zz_87 = ($signed(_zz_8) - $signed(_zz_88));
  assign _zz_88 = ($signed(_zz_89) * $signed(twiddle_factor_table_0_imag));
  assign _zz_89 = ($signed(data_mid_0_3_real) + $signed(data_mid_0_3_imag));
  assign _zz_90 = fixTo_6_dout;
  assign _zz_91 = ($signed(_zz_8) + $signed(_zz_92));
  assign _zz_92 = ($signed(_zz_93) * $signed(twiddle_factor_table_0_real));
  assign _zz_93 = ($signed(data_mid_0_3_imag) - $signed(data_mid_0_3_real));
  assign _zz_94 = fixTo_7_dout;
  assign _zz_95 = _zz_96[35 : 0];
  assign _zz_96 = _zz_97;
  assign _zz_97 = ($signed(_zz_98) >>> _zz_9);
  assign _zz_98 = _zz_99;
  assign _zz_99 = ($signed(_zz_101) - $signed(_zz_6));
  assign _zz_100 = ({9'd0,data_mid_0_2_real} <<< 9);
  assign _zz_101 = {{9{_zz_100[26]}}, _zz_100};
  assign _zz_102 = fixTo_8_dout;
  assign _zz_103 = _zz_104[35 : 0];
  assign _zz_104 = _zz_105;
  assign _zz_105 = ($signed(_zz_106) >>> _zz_9);
  assign _zz_106 = _zz_107;
  assign _zz_107 = ($signed(_zz_109) - $signed(_zz_7));
  assign _zz_108 = ({9'd0,data_mid_0_2_imag} <<< 9);
  assign _zz_109 = {{9{_zz_108[26]}}, _zz_108};
  assign _zz_110 = fixTo_9_dout;
  assign _zz_111 = _zz_112[35 : 0];
  assign _zz_112 = _zz_113;
  assign _zz_113 = ($signed(_zz_114) >>> _zz_10);
  assign _zz_114 = _zz_115;
  assign _zz_115 = ($signed(_zz_117) + $signed(_zz_6));
  assign _zz_116 = ({9'd0,data_mid_0_2_real} <<< 9);
  assign _zz_117 = {{9{_zz_116[26]}}, _zz_116};
  assign _zz_118 = fixTo_10_dout;
  assign _zz_119 = _zz_120[35 : 0];
  assign _zz_120 = _zz_121;
  assign _zz_121 = ($signed(_zz_122) >>> _zz_10);
  assign _zz_122 = _zz_123;
  assign _zz_123 = ($signed(_zz_125) + $signed(_zz_7));
  assign _zz_124 = ({9'd0,data_mid_0_2_imag} <<< 9);
  assign _zz_125 = {{9{_zz_124[26]}}, _zz_124};
  assign _zz_126 = fixTo_11_dout;
  assign _zz_127 = ($signed(twiddle_factor_table_1_real) + $signed(twiddle_factor_table_1_imag));
  assign _zz_128 = ($signed(_zz_13) - $signed(_zz_129));
  assign _zz_129 = ($signed(_zz_130) * $signed(twiddle_factor_table_1_imag));
  assign _zz_130 = ($signed(data_mid_1_2_real) + $signed(data_mid_1_2_imag));
  assign _zz_131 = fixTo_12_dout;
  assign _zz_132 = ($signed(_zz_13) + $signed(_zz_133));
  assign _zz_133 = ($signed(_zz_134) * $signed(twiddle_factor_table_1_real));
  assign _zz_134 = ($signed(data_mid_1_2_imag) - $signed(data_mid_1_2_real));
  assign _zz_135 = fixTo_13_dout;
  assign _zz_136 = _zz_137[35 : 0];
  assign _zz_137 = _zz_138;
  assign _zz_138 = ($signed(_zz_139) >>> _zz_14);
  assign _zz_139 = _zz_140;
  assign _zz_140 = ($signed(_zz_142) - $signed(_zz_11));
  assign _zz_141 = ({9'd0,data_mid_1_0_real} <<< 9);
  assign _zz_142 = {{9{_zz_141[26]}}, _zz_141};
  assign _zz_143 = fixTo_14_dout;
  assign _zz_144 = _zz_145[35 : 0];
  assign _zz_145 = _zz_146;
  assign _zz_146 = ($signed(_zz_147) >>> _zz_14);
  assign _zz_147 = _zz_148;
  assign _zz_148 = ($signed(_zz_150) - $signed(_zz_12));
  assign _zz_149 = ({9'd0,data_mid_1_0_imag} <<< 9);
  assign _zz_150 = {{9{_zz_149[26]}}, _zz_149};
  assign _zz_151 = fixTo_15_dout;
  assign _zz_152 = _zz_153[35 : 0];
  assign _zz_153 = _zz_154;
  assign _zz_154 = ($signed(_zz_155) >>> _zz_15);
  assign _zz_155 = _zz_156;
  assign _zz_156 = ($signed(_zz_158) + $signed(_zz_11));
  assign _zz_157 = ({9'd0,data_mid_1_0_real} <<< 9);
  assign _zz_158 = {{9{_zz_157[26]}}, _zz_157};
  assign _zz_159 = fixTo_16_dout;
  assign _zz_160 = _zz_161[35 : 0];
  assign _zz_161 = _zz_162;
  assign _zz_162 = ($signed(_zz_163) >>> _zz_15);
  assign _zz_163 = _zz_164;
  assign _zz_164 = ($signed(_zz_166) + $signed(_zz_12));
  assign _zz_165 = ({9'd0,data_mid_1_0_imag} <<< 9);
  assign _zz_166 = {{9{_zz_165[26]}}, _zz_165};
  assign _zz_167 = fixTo_17_dout;
  assign _zz_168 = ($signed(twiddle_factor_table_2_real) + $signed(twiddle_factor_table_2_imag));
  assign _zz_169 = ($signed(_zz_18) - $signed(_zz_170));
  assign _zz_170 = ($signed(_zz_171) * $signed(twiddle_factor_table_2_imag));
  assign _zz_171 = ($signed(data_mid_1_3_real) + $signed(data_mid_1_3_imag));
  assign _zz_172 = fixTo_18_dout;
  assign _zz_173 = ($signed(_zz_18) + $signed(_zz_174));
  assign _zz_174 = ($signed(_zz_175) * $signed(twiddle_factor_table_2_real));
  assign _zz_175 = ($signed(data_mid_1_3_imag) - $signed(data_mid_1_3_real));
  assign _zz_176 = fixTo_19_dout;
  assign _zz_177 = _zz_178[35 : 0];
  assign _zz_178 = _zz_179;
  assign _zz_179 = ($signed(_zz_180) >>> _zz_19);
  assign _zz_180 = _zz_181;
  assign _zz_181 = ($signed(_zz_183) - $signed(_zz_16));
  assign _zz_182 = ({9'd0,data_mid_1_1_real} <<< 9);
  assign _zz_183 = {{9{_zz_182[26]}}, _zz_182};
  assign _zz_184 = fixTo_20_dout;
  assign _zz_185 = _zz_186[35 : 0];
  assign _zz_186 = _zz_187;
  assign _zz_187 = ($signed(_zz_188) >>> _zz_19);
  assign _zz_188 = _zz_189;
  assign _zz_189 = ($signed(_zz_191) - $signed(_zz_17));
  assign _zz_190 = ({9'd0,data_mid_1_1_imag} <<< 9);
  assign _zz_191 = {{9{_zz_190[26]}}, _zz_190};
  assign _zz_192 = fixTo_21_dout;
  assign _zz_193 = _zz_194[35 : 0];
  assign _zz_194 = _zz_195;
  assign _zz_195 = ($signed(_zz_196) >>> _zz_20);
  assign _zz_196 = _zz_197;
  assign _zz_197 = ($signed(_zz_199) + $signed(_zz_16));
  assign _zz_198 = ({9'd0,data_mid_1_1_real} <<< 9);
  assign _zz_199 = {{9{_zz_198[26]}}, _zz_198};
  assign _zz_200 = fixTo_22_dout;
  assign _zz_201 = _zz_202[35 : 0];
  assign _zz_202 = _zz_203;
  assign _zz_203 = ($signed(_zz_204) >>> _zz_20);
  assign _zz_204 = _zz_205;
  assign _zz_205 = ($signed(_zz_207) + $signed(_zz_17));
  assign _zz_206 = ({9'd0,data_mid_1_1_imag} <<< 9);
  assign _zz_207 = {{9{_zz_206[26]}}, _zz_206};
  assign _zz_208 = fixTo_23_dout;
  SInt36fixTo35_0_ROUNDTOINF fixTo (
    .din     (_zz_21[35:0]      ), //i
    .dout    (fixTo_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_1 (
    .din     (_zz_22[35:0]        ), //i
    .dout    (fixTo_1_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_2 (
    .din     (_zz_23[35:0]        ), //i
    .dout    (fixTo_2_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_3 (
    .din     (_zz_24[35:0]        ), //i
    .dout    (fixTo_3_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_4 (
    .din     (_zz_25[35:0]        ), //i
    .dout    (fixTo_4_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_5 (
    .din     (_zz_26[35:0]        ), //i
    .dout    (fixTo_5_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_6 (
    .din     (_zz_27[35:0]        ), //i
    .dout    (fixTo_6_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_7 (
    .din     (_zz_28[35:0]        ), //i
    .dout    (fixTo_7_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_8 (
    .din     (_zz_29[35:0]        ), //i
    .dout    (fixTo_8_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_9 (
    .din     (_zz_30[35:0]        ), //i
    .dout    (fixTo_9_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_10 (
    .din     (_zz_31[35:0]         ), //i
    .dout    (fixTo_10_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_11 (
    .din     (_zz_32[35:0]         ), //i
    .dout    (fixTo_11_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_12 (
    .din     (_zz_33[35:0]         ), //i
    .dout    (fixTo_12_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_13 (
    .din     (_zz_34[35:0]         ), //i
    .dout    (fixTo_13_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_14 (
    .din     (_zz_35[35:0]         ), //i
    .dout    (fixTo_14_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_15 (
    .din     (_zz_36[35:0]         ), //i
    .dout    (fixTo_15_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_16 (
    .din     (_zz_37[35:0]         ), //i
    .dout    (fixTo_16_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_17 (
    .din     (_zz_38[35:0]         ), //i
    .dout    (fixTo_17_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_18 (
    .din     (_zz_39[35:0]         ), //i
    .dout    (fixTo_18_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_19 (
    .din     (_zz_40[35:0]         ), //i
    .dout    (fixTo_19_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_20 (
    .din     (_zz_41[35:0]         ), //i
    .dout    (fixTo_20_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_21 (
    .din     (_zz_42[35:0]         ), //i
    .dout    (fixTo_21_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_22 (
    .din     (_zz_43[35:0]         ), //i
    .dout    (fixTo_22_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_23 (
    .din     (_zz_44[35:0]         ), //i
    .dout    (fixTo_23_dout[17:0]  )  //o
  );
  assign twiddle_factor_table_0_real = 18'h00200;
  assign twiddle_factor_table_0_imag = 18'h0;
  assign twiddle_factor_table_1_real = 18'h00200;
  assign twiddle_factor_table_1_imag = 18'h0;
  assign twiddle_factor_table_2_real = 18'h0;
  assign twiddle_factor_table_2_imag = 18'h3fe00;
  assign data_reorder_0_real = data_in_0_real;
  assign data_reorder_0_imag = data_in_0_imag;
  assign data_reorder_2_real = data_in_1_real;
  assign data_reorder_2_imag = data_in_1_imag;
  assign data_reorder_1_real = data_in_2_real;
  assign data_reorder_1_imag = data_in_2_imag;
  assign data_reorder_3_real = data_in_3_real;
  assign data_reorder_3_imag = data_in_3_imag;
  assign _zz_3 = ($signed(_zz_45) * $signed(data_mid_0_1_real));
  assign _zz_21 = _zz_46;
  assign _zz_1 = _zz_49[35 : 0];
  assign _zz_22 = _zz_50;
  assign _zz_2 = _zz_53[35 : 0];
  assign _zz_4 = 1'b1;
  assign _zz_23 = _zz_54;
  assign _zz_24 = _zz_62;
  assign _zz_5 = 1'b1;
  assign _zz_25 = _zz_70;
  assign _zz_26 = _zz_78;
  assign _zz_8 = ($signed(_zz_86) * $signed(data_mid_0_3_real));
  assign _zz_27 = _zz_87;
  assign _zz_6 = _zz_90[35 : 0];
  assign _zz_28 = _zz_91;
  assign _zz_7 = _zz_94[35 : 0];
  assign _zz_9 = 1'b1;
  assign _zz_29 = _zz_95;
  assign _zz_30 = _zz_103;
  assign _zz_10 = 1'b1;
  assign _zz_31 = _zz_111;
  assign _zz_32 = _zz_119;
  assign _zz_13 = ($signed(_zz_127) * $signed(data_mid_1_2_real));
  assign _zz_33 = _zz_128;
  assign _zz_11 = _zz_131[35 : 0];
  assign _zz_34 = _zz_132;
  assign _zz_12 = _zz_135[35 : 0];
  assign _zz_14 = 1'b1;
  assign _zz_35 = _zz_136;
  assign _zz_36 = _zz_144;
  assign _zz_15 = 1'b1;
  assign _zz_37 = _zz_152;
  assign _zz_38 = _zz_160;
  assign _zz_18 = ($signed(_zz_168) * $signed(data_mid_1_3_real));
  assign _zz_39 = _zz_169;
  assign _zz_16 = _zz_172[35 : 0];
  assign _zz_40 = _zz_173;
  assign _zz_17 = _zz_176[35 : 0];
  assign _zz_19 = 1'b1;
  assign _zz_41 = _zz_177;
  assign _zz_42 = _zz_185;
  assign _zz_20 = 1'b1;
  assign _zz_43 = _zz_193;
  assign _zz_44 = _zz_201;
  assign fft_col_out_valid = io_data_in_valid_delay_4;
  assign fft_col_out_payload_0_real = data_mid_2_0_real;
  assign fft_col_out_payload_0_imag = data_mid_2_0_imag;
  assign fft_col_out_payload_1_real = data_mid_2_1_real;
  assign fft_col_out_payload_1_imag = data_mid_2_1_imag;
  assign fft_col_out_payload_2_real = data_mid_2_2_real;
  assign fft_col_out_payload_2_imag = data_mid_2_2_imag;
  assign fft_col_out_payload_3_real = data_mid_2_3_real;
  assign fft_col_out_payload_3_imag = data_mid_2_3_imag;
  always @ (posedge clk) begin
    if(io_data_in_valid)begin
      data_in_0_real <= io_data_in_payload_0_real;
      data_in_0_imag <= io_data_in_payload_0_imag;
      data_in_1_real <= io_data_in_payload_1_real;
      data_in_1_imag <= io_data_in_payload_1_imag;
      data_in_2_real <= io_data_in_payload_2_real;
      data_in_2_imag <= io_data_in_payload_2_imag;
      data_in_3_real <= io_data_in_payload_3_real;
      data_in_3_imag <= io_data_in_payload_3_imag;
    end
    data_mid_0_0_real <= data_reorder_0_real;
    data_mid_0_0_imag <= data_reorder_0_imag;
    data_mid_0_1_real <= data_reorder_1_real;
    data_mid_0_1_imag <= data_reorder_1_imag;
    data_mid_0_2_real <= data_reorder_2_real;
    data_mid_0_2_imag <= data_reorder_2_imag;
    data_mid_0_3_real <= data_reorder_3_real;
    data_mid_0_3_imag <= data_reorder_3_imag;
    data_mid_1_1_real <= _zz_61[17 : 0];
    data_mid_1_1_imag <= _zz_69[17 : 0];
    data_mid_1_0_real <= _zz_77[17 : 0];
    data_mid_1_0_imag <= _zz_85[17 : 0];
    data_mid_1_3_real <= _zz_102[17 : 0];
    data_mid_1_3_imag <= _zz_110[17 : 0];
    data_mid_1_2_real <= _zz_118[17 : 0];
    data_mid_1_2_imag <= _zz_126[17 : 0];
    data_mid_2_2_real <= _zz_143[17 : 0];
    data_mid_2_2_imag <= _zz_151[17 : 0];
    data_mid_2_0_real <= _zz_159[17 : 0];
    data_mid_2_0_imag <= _zz_167[17 : 0];
    data_mid_2_3_real <= _zz_184[17 : 0];
    data_mid_2_3_imag <= _zz_192[17 : 0];
    data_mid_2_1_real <= _zz_200[17 : 0];
    data_mid_2_1_imag <= _zz_208[17 : 0];
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      io_data_in_valid_delay_1 <= 1'b0;
      io_data_in_valid_delay_2 <= 1'b0;
      io_data_in_valid_delay_3 <= 1'b0;
      io_data_in_valid_delay_4 <= 1'b0;
    end else begin
      io_data_in_valid_delay_1 <= io_data_in_valid;
      io_data_in_valid_delay_2 <= io_data_in_valid_delay_1;
      io_data_in_valid_delay_3 <= io_data_in_valid_delay_2;
      io_data_in_valid_delay_4 <= io_data_in_valid_delay_3;
    end
  end


endmodule

module MyFFT (
  input               io_data_in_valid,
  input      [17:0]   io_data_in_payload_0_real,
  input      [17:0]   io_data_in_payload_0_imag,
  input      [17:0]   io_data_in_payload_1_real,
  input      [17:0]   io_data_in_payload_1_imag,
  input      [17:0]   io_data_in_payload_2_real,
  input      [17:0]   io_data_in_payload_2_imag,
  input      [17:0]   io_data_in_payload_3_real,
  input      [17:0]   io_data_in_payload_3_imag,
  output              fft_row_valid,
  output     [17:0]   fft_row_payload_0_real,
  output     [17:0]   fft_row_payload_0_imag,
  output     [17:0]   fft_row_payload_1_real,
  output     [17:0]   fft_row_payload_1_imag,
  output     [17:0]   fft_row_payload_2_real,
  output     [17:0]   fft_row_payload_2_imag,
  output     [17:0]   fft_row_payload_3_real,
  output     [17:0]   fft_row_payload_3_imag,
  input               clk,
  input               reset
);
  wire       [35:0]   _zz_21;
  wire       [35:0]   _zz_22;
  wire       [35:0]   _zz_23;
  wire       [35:0]   _zz_24;
  wire       [35:0]   _zz_25;
  wire       [35:0]   _zz_26;
  wire       [35:0]   _zz_27;
  wire       [35:0]   _zz_28;
  wire       [35:0]   _zz_29;
  wire       [35:0]   _zz_30;
  wire       [35:0]   _zz_31;
  wire       [35:0]   _zz_32;
  wire       [35:0]   _zz_33;
  wire       [35:0]   _zz_34;
  wire       [35:0]   _zz_35;
  wire       [35:0]   _zz_36;
  wire       [35:0]   _zz_37;
  wire       [35:0]   _zz_38;
  wire       [35:0]   _zz_39;
  wire       [35:0]   _zz_40;
  wire       [35:0]   _zz_41;
  wire       [35:0]   _zz_42;
  wire       [35:0]   _zz_43;
  wire       [35:0]   _zz_44;
  wire       [35:0]   fixTo_dout;
  wire       [35:0]   fixTo_1_dout;
  wire       [17:0]   fixTo_2_dout;
  wire       [17:0]   fixTo_3_dout;
  wire       [17:0]   fixTo_4_dout;
  wire       [17:0]   fixTo_5_dout;
  wire       [35:0]   fixTo_6_dout;
  wire       [35:0]   fixTo_7_dout;
  wire       [17:0]   fixTo_8_dout;
  wire       [17:0]   fixTo_9_dout;
  wire       [17:0]   fixTo_10_dout;
  wire       [17:0]   fixTo_11_dout;
  wire       [35:0]   fixTo_12_dout;
  wire       [35:0]   fixTo_13_dout;
  wire       [17:0]   fixTo_14_dout;
  wire       [17:0]   fixTo_15_dout;
  wire       [17:0]   fixTo_16_dout;
  wire       [17:0]   fixTo_17_dout;
  wire       [35:0]   fixTo_18_dout;
  wire       [35:0]   fixTo_19_dout;
  wire       [17:0]   fixTo_20_dout;
  wire       [17:0]   fixTo_21_dout;
  wire       [17:0]   fixTo_22_dout;
  wire       [17:0]   fixTo_23_dout;
  wire       [17:0]   _zz_45;
  wire       [35:0]   _zz_46;
  wire       [35:0]   _zz_47;
  wire       [17:0]   _zz_48;
  wire       [35:0]   _zz_49;
  wire       [35:0]   _zz_50;
  wire       [35:0]   _zz_51;
  wire       [17:0]   _zz_52;
  wire       [35:0]   _zz_53;
  wire       [35:0]   _zz_54;
  wire       [35:0]   _zz_55;
  wire       [35:0]   _zz_56;
  wire       [35:0]   _zz_57;
  wire       [35:0]   _zz_58;
  wire       [26:0]   _zz_59;
  wire       [35:0]   _zz_60;
  wire       [17:0]   _zz_61;
  wire       [35:0]   _zz_62;
  wire       [35:0]   _zz_63;
  wire       [35:0]   _zz_64;
  wire       [35:0]   _zz_65;
  wire       [35:0]   _zz_66;
  wire       [26:0]   _zz_67;
  wire       [35:0]   _zz_68;
  wire       [17:0]   _zz_69;
  wire       [35:0]   _zz_70;
  wire       [35:0]   _zz_71;
  wire       [35:0]   _zz_72;
  wire       [35:0]   _zz_73;
  wire       [35:0]   _zz_74;
  wire       [26:0]   _zz_75;
  wire       [35:0]   _zz_76;
  wire       [17:0]   _zz_77;
  wire       [35:0]   _zz_78;
  wire       [35:0]   _zz_79;
  wire       [35:0]   _zz_80;
  wire       [35:0]   _zz_81;
  wire       [35:0]   _zz_82;
  wire       [26:0]   _zz_83;
  wire       [35:0]   _zz_84;
  wire       [17:0]   _zz_85;
  wire       [17:0]   _zz_86;
  wire       [35:0]   _zz_87;
  wire       [35:0]   _zz_88;
  wire       [17:0]   _zz_89;
  wire       [35:0]   _zz_90;
  wire       [35:0]   _zz_91;
  wire       [35:0]   _zz_92;
  wire       [17:0]   _zz_93;
  wire       [35:0]   _zz_94;
  wire       [35:0]   _zz_95;
  wire       [35:0]   _zz_96;
  wire       [35:0]   _zz_97;
  wire       [35:0]   _zz_98;
  wire       [35:0]   _zz_99;
  wire       [26:0]   _zz_100;
  wire       [35:0]   _zz_101;
  wire       [17:0]   _zz_102;
  wire       [35:0]   _zz_103;
  wire       [35:0]   _zz_104;
  wire       [35:0]   _zz_105;
  wire       [35:0]   _zz_106;
  wire       [35:0]   _zz_107;
  wire       [26:0]   _zz_108;
  wire       [35:0]   _zz_109;
  wire       [17:0]   _zz_110;
  wire       [35:0]   _zz_111;
  wire       [35:0]   _zz_112;
  wire       [35:0]   _zz_113;
  wire       [35:0]   _zz_114;
  wire       [35:0]   _zz_115;
  wire       [26:0]   _zz_116;
  wire       [35:0]   _zz_117;
  wire       [17:0]   _zz_118;
  wire       [35:0]   _zz_119;
  wire       [35:0]   _zz_120;
  wire       [35:0]   _zz_121;
  wire       [35:0]   _zz_122;
  wire       [35:0]   _zz_123;
  wire       [26:0]   _zz_124;
  wire       [35:0]   _zz_125;
  wire       [17:0]   _zz_126;
  wire       [17:0]   _zz_127;
  wire       [35:0]   _zz_128;
  wire       [35:0]   _zz_129;
  wire       [17:0]   _zz_130;
  wire       [35:0]   _zz_131;
  wire       [35:0]   _zz_132;
  wire       [35:0]   _zz_133;
  wire       [17:0]   _zz_134;
  wire       [35:0]   _zz_135;
  wire       [35:0]   _zz_136;
  wire       [35:0]   _zz_137;
  wire       [35:0]   _zz_138;
  wire       [35:0]   _zz_139;
  wire       [35:0]   _zz_140;
  wire       [26:0]   _zz_141;
  wire       [35:0]   _zz_142;
  wire       [17:0]   _zz_143;
  wire       [35:0]   _zz_144;
  wire       [35:0]   _zz_145;
  wire       [35:0]   _zz_146;
  wire       [35:0]   _zz_147;
  wire       [35:0]   _zz_148;
  wire       [26:0]   _zz_149;
  wire       [35:0]   _zz_150;
  wire       [17:0]   _zz_151;
  wire       [35:0]   _zz_152;
  wire       [35:0]   _zz_153;
  wire       [35:0]   _zz_154;
  wire       [35:0]   _zz_155;
  wire       [35:0]   _zz_156;
  wire       [26:0]   _zz_157;
  wire       [35:0]   _zz_158;
  wire       [17:0]   _zz_159;
  wire       [35:0]   _zz_160;
  wire       [35:0]   _zz_161;
  wire       [35:0]   _zz_162;
  wire       [35:0]   _zz_163;
  wire       [35:0]   _zz_164;
  wire       [26:0]   _zz_165;
  wire       [35:0]   _zz_166;
  wire       [17:0]   _zz_167;
  wire       [17:0]   _zz_168;
  wire       [35:0]   _zz_169;
  wire       [35:0]   _zz_170;
  wire       [17:0]   _zz_171;
  wire       [35:0]   _zz_172;
  wire       [35:0]   _zz_173;
  wire       [35:0]   _zz_174;
  wire       [17:0]   _zz_175;
  wire       [35:0]   _zz_176;
  wire       [35:0]   _zz_177;
  wire       [35:0]   _zz_178;
  wire       [35:0]   _zz_179;
  wire       [35:0]   _zz_180;
  wire       [35:0]   _zz_181;
  wire       [26:0]   _zz_182;
  wire       [35:0]   _zz_183;
  wire       [17:0]   _zz_184;
  wire       [35:0]   _zz_185;
  wire       [35:0]   _zz_186;
  wire       [35:0]   _zz_187;
  wire       [35:0]   _zz_188;
  wire       [35:0]   _zz_189;
  wire       [26:0]   _zz_190;
  wire       [35:0]   _zz_191;
  wire       [17:0]   _zz_192;
  wire       [35:0]   _zz_193;
  wire       [35:0]   _zz_194;
  wire       [35:0]   _zz_195;
  wire       [35:0]   _zz_196;
  wire       [35:0]   _zz_197;
  wire       [26:0]   _zz_198;
  wire       [35:0]   _zz_199;
  wire       [17:0]   _zz_200;
  wire       [35:0]   _zz_201;
  wire       [35:0]   _zz_202;
  wire       [35:0]   _zz_203;
  wire       [35:0]   _zz_204;
  wire       [35:0]   _zz_205;
  wire       [26:0]   _zz_206;
  wire       [35:0]   _zz_207;
  wire       [17:0]   _zz_208;
  reg        [17:0]   data_in_0_real;
  reg        [17:0]   data_in_0_imag;
  reg        [17:0]   data_in_1_real;
  reg        [17:0]   data_in_1_imag;
  reg        [17:0]   data_in_2_real;
  reg        [17:0]   data_in_2_imag;
  reg        [17:0]   data_in_3_real;
  reg        [17:0]   data_in_3_imag;
  wire       [17:0]   twiddle_factor_table_0_real;
  wire       [17:0]   twiddle_factor_table_0_imag;
  wire       [17:0]   twiddle_factor_table_1_real;
  wire       [17:0]   twiddle_factor_table_1_imag;
  wire       [17:0]   twiddle_factor_table_2_real;
  wire       [17:0]   twiddle_factor_table_2_imag;
  wire       [17:0]   data_reorder_0_real;
  wire       [17:0]   data_reorder_0_imag;
  wire       [17:0]   data_reorder_1_real;
  wire       [17:0]   data_reorder_1_imag;
  wire       [17:0]   data_reorder_2_real;
  wire       [17:0]   data_reorder_2_imag;
  wire       [17:0]   data_reorder_3_real;
  wire       [17:0]   data_reorder_3_imag;
  reg        [17:0]   data_mid_0_0_real;
  reg        [17:0]   data_mid_0_0_imag;
  reg        [17:0]   data_mid_0_1_real;
  reg        [17:0]   data_mid_0_1_imag;
  reg        [17:0]   data_mid_0_2_real;
  reg        [17:0]   data_mid_0_2_imag;
  reg        [17:0]   data_mid_0_3_real;
  reg        [17:0]   data_mid_0_3_imag;
  reg        [17:0]   data_mid_1_0_real;
  reg        [17:0]   data_mid_1_0_imag;
  reg        [17:0]   data_mid_1_1_real;
  reg        [17:0]   data_mid_1_1_imag;
  reg        [17:0]   data_mid_1_2_real;
  reg        [17:0]   data_mid_1_2_imag;
  reg        [17:0]   data_mid_1_3_real;
  reg        [17:0]   data_mid_1_3_imag;
  reg        [17:0]   data_mid_2_0_real;
  reg        [17:0]   data_mid_2_0_imag;
  reg        [17:0]   data_mid_2_1_real;
  reg        [17:0]   data_mid_2_1_imag;
  reg        [17:0]   data_mid_2_2_real;
  reg        [17:0]   data_mid_2_2_imag;
  reg        [17:0]   data_mid_2_3_real;
  reg        [17:0]   data_mid_2_3_imag;
  wire       [35:0]   _zz_1;
  wire       [35:0]   _zz_2;
  wire       [35:0]   _zz_3;
  wire       [0:0]    _zz_4;
  wire       [0:0]    _zz_5;
  wire       [35:0]   _zz_6;
  wire       [35:0]   _zz_7;
  wire       [35:0]   _zz_8;
  wire       [0:0]    _zz_9;
  wire       [0:0]    _zz_10;
  wire       [35:0]   _zz_11;
  wire       [35:0]   _zz_12;
  wire       [35:0]   _zz_13;
  wire       [0:0]    _zz_14;
  wire       [0:0]    _zz_15;
  wire       [35:0]   _zz_16;
  wire       [35:0]   _zz_17;
  wire       [35:0]   _zz_18;
  wire       [0:0]    _zz_19;
  wire       [0:0]    _zz_20;
  reg                 io_data_in_valid_delay_1;
  reg                 io_data_in_valid_delay_2;
  reg                 io_data_in_valid_delay_3;
  reg                 io_data_in_valid_delay_4;

  assign _zz_45 = ($signed(twiddle_factor_table_0_real) + $signed(twiddle_factor_table_0_imag));
  assign _zz_46 = ($signed(_zz_3) - $signed(_zz_47));
  assign _zz_47 = ($signed(_zz_48) * $signed(twiddle_factor_table_0_imag));
  assign _zz_48 = ($signed(data_mid_0_1_real) + $signed(data_mid_0_1_imag));
  assign _zz_49 = fixTo_dout;
  assign _zz_50 = ($signed(_zz_3) + $signed(_zz_51));
  assign _zz_51 = ($signed(_zz_52) * $signed(twiddle_factor_table_0_real));
  assign _zz_52 = ($signed(data_mid_0_1_imag) - $signed(data_mid_0_1_real));
  assign _zz_53 = fixTo_1_dout;
  assign _zz_54 = _zz_55[35 : 0];
  assign _zz_55 = _zz_56;
  assign _zz_56 = ($signed(_zz_57) >>> _zz_4);
  assign _zz_57 = _zz_58;
  assign _zz_58 = ($signed(_zz_60) - $signed(_zz_1));
  assign _zz_59 = ({9'd0,data_mid_0_0_real} <<< 9);
  assign _zz_60 = {{9{_zz_59[26]}}, _zz_59};
  assign _zz_61 = fixTo_2_dout;
  assign _zz_62 = _zz_63[35 : 0];
  assign _zz_63 = _zz_64;
  assign _zz_64 = ($signed(_zz_65) >>> _zz_4);
  assign _zz_65 = _zz_66;
  assign _zz_66 = ($signed(_zz_68) - $signed(_zz_2));
  assign _zz_67 = ({9'd0,data_mid_0_0_imag} <<< 9);
  assign _zz_68 = {{9{_zz_67[26]}}, _zz_67};
  assign _zz_69 = fixTo_3_dout;
  assign _zz_70 = _zz_71[35 : 0];
  assign _zz_71 = _zz_72;
  assign _zz_72 = ($signed(_zz_73) >>> _zz_5);
  assign _zz_73 = _zz_74;
  assign _zz_74 = ($signed(_zz_76) + $signed(_zz_1));
  assign _zz_75 = ({9'd0,data_mid_0_0_real} <<< 9);
  assign _zz_76 = {{9{_zz_75[26]}}, _zz_75};
  assign _zz_77 = fixTo_4_dout;
  assign _zz_78 = _zz_79[35 : 0];
  assign _zz_79 = _zz_80;
  assign _zz_80 = ($signed(_zz_81) >>> _zz_5);
  assign _zz_81 = _zz_82;
  assign _zz_82 = ($signed(_zz_84) + $signed(_zz_2));
  assign _zz_83 = ({9'd0,data_mid_0_0_imag} <<< 9);
  assign _zz_84 = {{9{_zz_83[26]}}, _zz_83};
  assign _zz_85 = fixTo_5_dout;
  assign _zz_86 = ($signed(twiddle_factor_table_0_real) + $signed(twiddle_factor_table_0_imag));
  assign _zz_87 = ($signed(_zz_8) - $signed(_zz_88));
  assign _zz_88 = ($signed(_zz_89) * $signed(twiddle_factor_table_0_imag));
  assign _zz_89 = ($signed(data_mid_0_3_real) + $signed(data_mid_0_3_imag));
  assign _zz_90 = fixTo_6_dout;
  assign _zz_91 = ($signed(_zz_8) + $signed(_zz_92));
  assign _zz_92 = ($signed(_zz_93) * $signed(twiddle_factor_table_0_real));
  assign _zz_93 = ($signed(data_mid_0_3_imag) - $signed(data_mid_0_3_real));
  assign _zz_94 = fixTo_7_dout;
  assign _zz_95 = _zz_96[35 : 0];
  assign _zz_96 = _zz_97;
  assign _zz_97 = ($signed(_zz_98) >>> _zz_9);
  assign _zz_98 = _zz_99;
  assign _zz_99 = ($signed(_zz_101) - $signed(_zz_6));
  assign _zz_100 = ({9'd0,data_mid_0_2_real} <<< 9);
  assign _zz_101 = {{9{_zz_100[26]}}, _zz_100};
  assign _zz_102 = fixTo_8_dout;
  assign _zz_103 = _zz_104[35 : 0];
  assign _zz_104 = _zz_105;
  assign _zz_105 = ($signed(_zz_106) >>> _zz_9);
  assign _zz_106 = _zz_107;
  assign _zz_107 = ($signed(_zz_109) - $signed(_zz_7));
  assign _zz_108 = ({9'd0,data_mid_0_2_imag} <<< 9);
  assign _zz_109 = {{9{_zz_108[26]}}, _zz_108};
  assign _zz_110 = fixTo_9_dout;
  assign _zz_111 = _zz_112[35 : 0];
  assign _zz_112 = _zz_113;
  assign _zz_113 = ($signed(_zz_114) >>> _zz_10);
  assign _zz_114 = _zz_115;
  assign _zz_115 = ($signed(_zz_117) + $signed(_zz_6));
  assign _zz_116 = ({9'd0,data_mid_0_2_real} <<< 9);
  assign _zz_117 = {{9{_zz_116[26]}}, _zz_116};
  assign _zz_118 = fixTo_10_dout;
  assign _zz_119 = _zz_120[35 : 0];
  assign _zz_120 = _zz_121;
  assign _zz_121 = ($signed(_zz_122) >>> _zz_10);
  assign _zz_122 = _zz_123;
  assign _zz_123 = ($signed(_zz_125) + $signed(_zz_7));
  assign _zz_124 = ({9'd0,data_mid_0_2_imag} <<< 9);
  assign _zz_125 = {{9{_zz_124[26]}}, _zz_124};
  assign _zz_126 = fixTo_11_dout;
  assign _zz_127 = ($signed(twiddle_factor_table_1_real) + $signed(twiddle_factor_table_1_imag));
  assign _zz_128 = ($signed(_zz_13) - $signed(_zz_129));
  assign _zz_129 = ($signed(_zz_130) * $signed(twiddle_factor_table_1_imag));
  assign _zz_130 = ($signed(data_mid_1_2_real) + $signed(data_mid_1_2_imag));
  assign _zz_131 = fixTo_12_dout;
  assign _zz_132 = ($signed(_zz_13) + $signed(_zz_133));
  assign _zz_133 = ($signed(_zz_134) * $signed(twiddle_factor_table_1_real));
  assign _zz_134 = ($signed(data_mid_1_2_imag) - $signed(data_mid_1_2_real));
  assign _zz_135 = fixTo_13_dout;
  assign _zz_136 = _zz_137[35 : 0];
  assign _zz_137 = _zz_138;
  assign _zz_138 = ($signed(_zz_139) >>> _zz_14);
  assign _zz_139 = _zz_140;
  assign _zz_140 = ($signed(_zz_142) - $signed(_zz_11));
  assign _zz_141 = ({9'd0,data_mid_1_0_real} <<< 9);
  assign _zz_142 = {{9{_zz_141[26]}}, _zz_141};
  assign _zz_143 = fixTo_14_dout;
  assign _zz_144 = _zz_145[35 : 0];
  assign _zz_145 = _zz_146;
  assign _zz_146 = ($signed(_zz_147) >>> _zz_14);
  assign _zz_147 = _zz_148;
  assign _zz_148 = ($signed(_zz_150) - $signed(_zz_12));
  assign _zz_149 = ({9'd0,data_mid_1_0_imag} <<< 9);
  assign _zz_150 = {{9{_zz_149[26]}}, _zz_149};
  assign _zz_151 = fixTo_15_dout;
  assign _zz_152 = _zz_153[35 : 0];
  assign _zz_153 = _zz_154;
  assign _zz_154 = ($signed(_zz_155) >>> _zz_15);
  assign _zz_155 = _zz_156;
  assign _zz_156 = ($signed(_zz_158) + $signed(_zz_11));
  assign _zz_157 = ({9'd0,data_mid_1_0_real} <<< 9);
  assign _zz_158 = {{9{_zz_157[26]}}, _zz_157};
  assign _zz_159 = fixTo_16_dout;
  assign _zz_160 = _zz_161[35 : 0];
  assign _zz_161 = _zz_162;
  assign _zz_162 = ($signed(_zz_163) >>> _zz_15);
  assign _zz_163 = _zz_164;
  assign _zz_164 = ($signed(_zz_166) + $signed(_zz_12));
  assign _zz_165 = ({9'd0,data_mid_1_0_imag} <<< 9);
  assign _zz_166 = {{9{_zz_165[26]}}, _zz_165};
  assign _zz_167 = fixTo_17_dout;
  assign _zz_168 = ($signed(twiddle_factor_table_2_real) + $signed(twiddle_factor_table_2_imag));
  assign _zz_169 = ($signed(_zz_18) - $signed(_zz_170));
  assign _zz_170 = ($signed(_zz_171) * $signed(twiddle_factor_table_2_imag));
  assign _zz_171 = ($signed(data_mid_1_3_real) + $signed(data_mid_1_3_imag));
  assign _zz_172 = fixTo_18_dout;
  assign _zz_173 = ($signed(_zz_18) + $signed(_zz_174));
  assign _zz_174 = ($signed(_zz_175) * $signed(twiddle_factor_table_2_real));
  assign _zz_175 = ($signed(data_mid_1_3_imag) - $signed(data_mid_1_3_real));
  assign _zz_176 = fixTo_19_dout;
  assign _zz_177 = _zz_178[35 : 0];
  assign _zz_178 = _zz_179;
  assign _zz_179 = ($signed(_zz_180) >>> _zz_19);
  assign _zz_180 = _zz_181;
  assign _zz_181 = ($signed(_zz_183) - $signed(_zz_16));
  assign _zz_182 = ({9'd0,data_mid_1_1_real} <<< 9);
  assign _zz_183 = {{9{_zz_182[26]}}, _zz_182};
  assign _zz_184 = fixTo_20_dout;
  assign _zz_185 = _zz_186[35 : 0];
  assign _zz_186 = _zz_187;
  assign _zz_187 = ($signed(_zz_188) >>> _zz_19);
  assign _zz_188 = _zz_189;
  assign _zz_189 = ($signed(_zz_191) - $signed(_zz_17));
  assign _zz_190 = ({9'd0,data_mid_1_1_imag} <<< 9);
  assign _zz_191 = {{9{_zz_190[26]}}, _zz_190};
  assign _zz_192 = fixTo_21_dout;
  assign _zz_193 = _zz_194[35 : 0];
  assign _zz_194 = _zz_195;
  assign _zz_195 = ($signed(_zz_196) >>> _zz_20);
  assign _zz_196 = _zz_197;
  assign _zz_197 = ($signed(_zz_199) + $signed(_zz_16));
  assign _zz_198 = ({9'd0,data_mid_1_1_real} <<< 9);
  assign _zz_199 = {{9{_zz_198[26]}}, _zz_198};
  assign _zz_200 = fixTo_22_dout;
  assign _zz_201 = _zz_202[35 : 0];
  assign _zz_202 = _zz_203;
  assign _zz_203 = ($signed(_zz_204) >>> _zz_20);
  assign _zz_204 = _zz_205;
  assign _zz_205 = ($signed(_zz_207) + $signed(_zz_17));
  assign _zz_206 = ({9'd0,data_mid_1_1_imag} <<< 9);
  assign _zz_207 = {{9{_zz_206[26]}}, _zz_206};
  assign _zz_208 = fixTo_23_dout;
  SInt36fixTo35_0_ROUNDTOINF fixTo (
    .din     (_zz_21[35:0]      ), //i
    .dout    (fixTo_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_1 (
    .din     (_zz_22[35:0]        ), //i
    .dout    (fixTo_1_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_2 (
    .din     (_zz_23[35:0]        ), //i
    .dout    (fixTo_2_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_3 (
    .din     (_zz_24[35:0]        ), //i
    .dout    (fixTo_3_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_4 (
    .din     (_zz_25[35:0]        ), //i
    .dout    (fixTo_4_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_5 (
    .din     (_zz_26[35:0]        ), //i
    .dout    (fixTo_5_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_6 (
    .din     (_zz_27[35:0]        ), //i
    .dout    (fixTo_6_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_7 (
    .din     (_zz_28[35:0]        ), //i
    .dout    (fixTo_7_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_8 (
    .din     (_zz_29[35:0]        ), //i
    .dout    (fixTo_8_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_9 (
    .din     (_zz_30[35:0]        ), //i
    .dout    (fixTo_9_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_10 (
    .din     (_zz_31[35:0]         ), //i
    .dout    (fixTo_10_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_11 (
    .din     (_zz_32[35:0]         ), //i
    .dout    (fixTo_11_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_12 (
    .din     (_zz_33[35:0]         ), //i
    .dout    (fixTo_12_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_13 (
    .din     (_zz_34[35:0]         ), //i
    .dout    (fixTo_13_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_14 (
    .din     (_zz_35[35:0]         ), //i
    .dout    (fixTo_14_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_15 (
    .din     (_zz_36[35:0]         ), //i
    .dout    (fixTo_15_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_16 (
    .din     (_zz_37[35:0]         ), //i
    .dout    (fixTo_16_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_17 (
    .din     (_zz_38[35:0]         ), //i
    .dout    (fixTo_17_dout[17:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_18 (
    .din     (_zz_39[35:0]         ), //i
    .dout    (fixTo_18_dout[35:0]  )  //o
  );
  SInt36fixTo35_0_ROUNDTOINF fixTo_19 (
    .din     (_zz_40[35:0]         ), //i
    .dout    (fixTo_19_dout[35:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_20 (
    .din     (_zz_41[35:0]         ), //i
    .dout    (fixTo_20_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_21 (
    .din     (_zz_42[35:0]         ), //i
    .dout    (fixTo_21_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_22 (
    .din     (_zz_43[35:0]         ), //i
    .dout    (fixTo_22_dout[17:0]  )  //o
  );
  SInt36fixTo26_9_ROUNDTOINF fixTo_23 (
    .din     (_zz_44[35:0]         ), //i
    .dout    (fixTo_23_dout[17:0]  )  //o
  );
  assign twiddle_factor_table_0_real = 18'h00200;
  assign twiddle_factor_table_0_imag = 18'h0;
  assign twiddle_factor_table_1_real = 18'h00200;
  assign twiddle_factor_table_1_imag = 18'h0;
  assign twiddle_factor_table_2_real = 18'h0;
  assign twiddle_factor_table_2_imag = 18'h3fe00;
  assign data_reorder_0_real = data_in_0_real;
  assign data_reorder_0_imag = data_in_0_imag;
  assign data_reorder_2_real = data_in_1_real;
  assign data_reorder_2_imag = data_in_1_imag;
  assign data_reorder_1_real = data_in_2_real;
  assign data_reorder_1_imag = data_in_2_imag;
  assign data_reorder_3_real = data_in_3_real;
  assign data_reorder_3_imag = data_in_3_imag;
  assign _zz_3 = ($signed(_zz_45) * $signed(data_mid_0_1_real));
  assign _zz_21 = _zz_46;
  assign _zz_1 = _zz_49[35 : 0];
  assign _zz_22 = _zz_50;
  assign _zz_2 = _zz_53[35 : 0];
  assign _zz_4 = 1'b1;
  assign _zz_23 = _zz_54;
  assign _zz_24 = _zz_62;
  assign _zz_5 = 1'b1;
  assign _zz_25 = _zz_70;
  assign _zz_26 = _zz_78;
  assign _zz_8 = ($signed(_zz_86) * $signed(data_mid_0_3_real));
  assign _zz_27 = _zz_87;
  assign _zz_6 = _zz_90[35 : 0];
  assign _zz_28 = _zz_91;
  assign _zz_7 = _zz_94[35 : 0];
  assign _zz_9 = 1'b1;
  assign _zz_29 = _zz_95;
  assign _zz_30 = _zz_103;
  assign _zz_10 = 1'b1;
  assign _zz_31 = _zz_111;
  assign _zz_32 = _zz_119;
  assign _zz_13 = ($signed(_zz_127) * $signed(data_mid_1_2_real));
  assign _zz_33 = _zz_128;
  assign _zz_11 = _zz_131[35 : 0];
  assign _zz_34 = _zz_132;
  assign _zz_12 = _zz_135[35 : 0];
  assign _zz_14 = 1'b1;
  assign _zz_35 = _zz_136;
  assign _zz_36 = _zz_144;
  assign _zz_15 = 1'b1;
  assign _zz_37 = _zz_152;
  assign _zz_38 = _zz_160;
  assign _zz_18 = ($signed(_zz_168) * $signed(data_mid_1_3_real));
  assign _zz_39 = _zz_169;
  assign _zz_16 = _zz_172[35 : 0];
  assign _zz_40 = _zz_173;
  assign _zz_17 = _zz_176[35 : 0];
  assign _zz_19 = 1'b1;
  assign _zz_41 = _zz_177;
  assign _zz_42 = _zz_185;
  assign _zz_20 = 1'b1;
  assign _zz_43 = _zz_193;
  assign _zz_44 = _zz_201;
  assign fft_row_valid = io_data_in_valid_delay_4;
  assign fft_row_payload_0_real = data_mid_2_0_real;
  assign fft_row_payload_0_imag = data_mid_2_0_imag;
  assign fft_row_payload_1_real = data_mid_2_1_real;
  assign fft_row_payload_1_imag = data_mid_2_1_imag;
  assign fft_row_payload_2_real = data_mid_2_2_real;
  assign fft_row_payload_2_imag = data_mid_2_2_imag;
  assign fft_row_payload_3_real = data_mid_2_3_real;
  assign fft_row_payload_3_imag = data_mid_2_3_imag;
  always @ (posedge clk) begin
    if(io_data_in_valid)begin
      data_in_0_real <= io_data_in_payload_0_real;
      data_in_0_imag <= io_data_in_payload_0_imag;
      data_in_1_real <= io_data_in_payload_1_real;
      data_in_1_imag <= io_data_in_payload_1_imag;
      data_in_2_real <= io_data_in_payload_2_real;
      data_in_2_imag <= io_data_in_payload_2_imag;
      data_in_3_real <= io_data_in_payload_3_real;
      data_in_3_imag <= io_data_in_payload_3_imag;
    end
    data_mid_0_0_real <= data_reorder_0_real;
    data_mid_0_0_imag <= data_reorder_0_imag;
    data_mid_0_1_real <= data_reorder_1_real;
    data_mid_0_1_imag <= data_reorder_1_imag;
    data_mid_0_2_real <= data_reorder_2_real;
    data_mid_0_2_imag <= data_reorder_2_imag;
    data_mid_0_3_real <= data_reorder_3_real;
    data_mid_0_3_imag <= data_reorder_3_imag;
    data_mid_1_1_real <= _zz_61[17 : 0];
    data_mid_1_1_imag <= _zz_69[17 : 0];
    data_mid_1_0_real <= _zz_77[17 : 0];
    data_mid_1_0_imag <= _zz_85[17 : 0];
    data_mid_1_3_real <= _zz_102[17 : 0];
    data_mid_1_3_imag <= _zz_110[17 : 0];
    data_mid_1_2_real <= _zz_118[17 : 0];
    data_mid_1_2_imag <= _zz_126[17 : 0];
    data_mid_2_2_real <= _zz_143[17 : 0];
    data_mid_2_2_imag <= _zz_151[17 : 0];
    data_mid_2_0_real <= _zz_159[17 : 0];
    data_mid_2_0_imag <= _zz_167[17 : 0];
    data_mid_2_3_real <= _zz_184[17 : 0];
    data_mid_2_3_imag <= _zz_192[17 : 0];
    data_mid_2_1_real <= _zz_200[17 : 0];
    data_mid_2_1_imag <= _zz_208[17 : 0];
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      io_data_in_valid_delay_1 <= 1'b0;
      io_data_in_valid_delay_2 <= 1'b0;
      io_data_in_valid_delay_3 <= 1'b0;
      io_data_in_valid_delay_4 <= 1'b0;
    end else begin
      io_data_in_valid_delay_1 <= io_data_in_valid;
      io_data_in_valid_delay_2 <= io_data_in_valid_delay_1;
      io_data_in_valid_delay_3 <= io_data_in_valid_delay_2;
      io_data_in_valid_delay_4 <= io_data_in_valid_delay_3;
    end
  end


endmodule

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

//SInt36fixTo26_9_ROUNDTOINF replaced by SInt36fixTo26_9_ROUNDTOINF

module SInt36fixTo26_9_ROUNDTOINF (
  input      [35:0]   din,
  output     [17:0]   dout
);
  wire       [36:0]   _zz_9;
  wire       [36:0]   _zz_10;
  wire       [8:0]    _zz_11;
  wire       [27:0]   _zz_12;
  wire       [27:0]   _zz_13;
  wire       [36:0]   _zz_14;
  wire       [36:0]   _zz_15;
  wire       [36:0]   _zz_16;
  wire       [10:0]   _zz_17;
  wire       [9:0]    _zz_18;
  reg        [27:0]   _zz_1;
  wire       [35:0]   _zz_2;
  wire       [35:0]   _zz_3;
  wire       [35:0]   _zz_4;
  wire       [36:0]   _zz_5;
  wire       [35:0]   _zz_6;
  reg        [27:0]   _zz_7;
  reg        [17:0]   _zz_8;

  assign _zz_9 = {_zz_4[35],_zz_4};
  assign _zz_10 = {_zz_3[35],_zz_3};
  assign _zz_11 = _zz_5[8 : 0];
  assign _zz_12 = _zz_5[36 : 9];
  assign _zz_13 = 28'h0000001;
  assign _zz_14 = ($signed(_zz_15) + $signed(_zz_16));
  assign _zz_15 = {_zz_6[35],_zz_6};
  assign _zz_16 = {_zz_2[35],_zz_2};
  assign _zz_17 = _zz_1[27 : 17];
  assign _zz_18 = _zz_1[26 : 17];
  assign _zz_2 = {{27'h0,1'b1},8'h0};
  assign _zz_3 = {28'hfffffff,8'h0};
  assign _zz_4 = din[35 : 0];
  assign _zz_5 = ($signed(_zz_9) + $signed(_zz_10));
  assign _zz_6 = din[35 : 0];
  always @ (*) begin
    if((_zz_11 != 9'h0))begin
      _zz_7 = ($signed(_zz_12) + $signed(_zz_13));
    end else begin
      _zz_7 = _zz_5[36 : 9];
    end
  end

  always @ (*) begin
    if(_zz_5[36])begin
      _zz_1 = _zz_7;
    end else begin
      _zz_1 = (_zz_14 >>> 9);
    end
  end

  always @ (*) begin
    if(_zz_1[27])begin
      if((! (_zz_17 == 11'h7ff)))begin
        _zz_8 = 18'h20000;
      end else begin
        _zz_8 = _zz_1[17 : 0];
      end
    end else begin
      if((_zz_18 != 10'h0))begin
        _zz_8 = 18'h1ffff;
      end else begin
        _zz_8 = _zz_1[17 : 0];
      end
    end
  end

  assign dout = _zz_8;

endmodule

//SInt36fixTo35_0_ROUNDTOINF replaced by SInt36fixTo35_0_ROUNDTOINF

module SInt36fixTo35_0_ROUNDTOINF (
  input      [35:0]   din,
  output     [35:0]   dout
);

  assign dout = din;

endmodule
