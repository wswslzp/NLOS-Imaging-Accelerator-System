// Generator : SpinalHDL v1.4.2    git head : d65b8c01ad060f0c2a8c80ed1b1e9da6e6240532
// Component : HT
// Git hash  : d151d69c0adb8fba5bc1372ea3c015e94de78c79



module HT (
  input      [31:0]   dati,
  output     [32:0]   dato,
  input               clk,
  input               reset
);
  wire       [31:0]   _zz_1;
  wire       [31:0]   his_0;
  reg        [31:0]   his_1;
  reg        [31:0]   his_2;
  reg        [31:0]   his_3;
  reg        [31:0]   his_4;
  reg        [31:0]   his_5;
  reg        [31:0]   his_6;
  reg        [31:0]   his_7;
  reg        [31:0]   his_8;
  reg        [31:0]   his_9;
  wire       [31:0]   his_1_0;
  wire       [31:0]   his_1_1;
  wire       [31:0]   his_1_2;
  wire       [31:0]   his_1_3;
  wire       [31:0]   his_1_4;
  wire       [31:0]   his_1_5;
  wire       [31:0]   his_1_6;
  wire       [31:0]   his_1_7;
  wire       [31:0]   his_1_8;
  wire       [31:0]   his_1_9;

  assign _zz_1 = (((((((((his_1_0 | his_1_1) | his_1_2) | his_1_3) | his_1_4) | his_1_5) | his_1_6) | his_1_7) | his_1_8) | his_1_9);
  assign his_0 = dati;
  assign his_1_0 = (his_0 + 32'h0);
  assign his_1_1 = (his_1 + 32'h00000001);
  assign his_1_2 = (his_2 + 32'h00000002);
  assign his_1_3 = (his_3 + 32'h00000003);
  assign his_1_4 = (his_4 + 32'h00000004);
  assign his_1_5 = (his_5 + 32'h00000005);
  assign his_1_6 = (his_6 + 32'h00000006);
  assign his_1_7 = (his_7 + 32'h00000007);
  assign his_1_8 = (his_8 + 32'h00000008);
  assign his_1_9 = (his_9 + 32'h00000009);
  assign dato = {1'd0, _zz_1};
  always @ (posedge clk) begin
    his_1 <= his_0;
    his_2 <= his_1;
    his_3 <= his_2;
    his_4 <= his_3;
    his_5 <= his_4;
    his_6 <= his_5;
    his_7 <= his_6;
    his_8 <= his_7;
    his_9 <= his_8;
  end


endmodule
