// Generator : SpinalHDL v1.4.1    git head : d1b4746673438bc5f242515335278fa39a666c38
// Component : TopLevel
// Git hash  : 5b9162698fb44411d3965102ba2614672aab0931


`define StateMachineEnum_binary_sequential_type [1:0]
`define StateMachineEnum_binary_sequential_boot 2'b00
`define StateMachineEnum_binary_sequential_e1 2'b01
`define StateMachineEnum_binary_sequential_e2 2'b10

`define StateMachineEnum_1_binary_sequential_type [1:0]
`define StateMachineEnum_1_binary_sequential_boot 2'b00
`define StateMachineEnum_1_binary_sequential_e1 2'b01
`define StateMachineEnum_1_binary_sequential_e2 2'b10

`define fsm_enumDefinition_binary_sequential_type [2:0]
`define fsm_enumDefinition_binary_sequential_boot 3'b000
`define fsm_enumDefinition_binary_sequential_fsm_stateA 3'b001
`define fsm_enumDefinition_binary_sequential_fsm_stateB 3'b010
`define fsm_enumDefinition_binary_sequential_fsm_stateC 3'b011
`define fsm_enumDefinition_binary_sequential_fsm_stateD 3'b100


module TopLevel (
  output reg          io_result,
  output reg          io_when_is_next,
  output reg          io_int_fsm1_flag,
  output reg          io_int_fsm2_flag,
  input               clk,
  input               reset
);
  wire                _zz_12;
  wire                _zz_13;
  wire                _zz_14;
  wire                fsm_wantExit;
  reg        [3:0]    _zz_1;
  reg                 _zz_2;
  reg        [1:0]    _zz_3;
  reg                 _zz_4;
  reg        [2:0]    _zz_5;
  reg        [7:0]    fsm_counter;
  reg        `StateMachineEnum_binary_sequential_type _zz_6;
  reg        `StateMachineEnum_binary_sequential_type _zz_7;
  reg        `StateMachineEnum_1_binary_sequential_type _zz_8;
  reg        `StateMachineEnum_1_binary_sequential_type _zz_9;
  reg        `fsm_enumDefinition_binary_sequential_type fsm_stateReg;
  reg        `fsm_enumDefinition_binary_sequential_type fsm_stateNext;
  wire                _zz_10;
  wire                _zz_11;
  `ifndef SYNTHESIS
  reg [31:0] _zz_6_string;
  reg [31:0] _zz_7_string;
  reg [31:0] _zz_8_string;
  reg [31:0] _zz_9_string;
  reg [79:0] fsm_stateReg_string;
  reg [79:0] fsm_stateNext_string;
  `endif


  assign _zz_12 = (_zz_3 <= 2'b01);
  assign _zz_13 = (_zz_5 <= 3'b001);
  assign _zz_14 = ((! (fsm_stateReg == `fsm_enumDefinition_binary_sequential_fsm_stateD)) && (fsm_stateNext == `fsm_enumDefinition_binary_sequential_fsm_stateD));
  `ifndef SYNTHESIS
  always @(*) begin
    case(_zz_6)
      `StateMachineEnum_binary_sequential_boot : _zz_6_string = "boot";
      `StateMachineEnum_binary_sequential_e1 : _zz_6_string = "e1  ";
      `StateMachineEnum_binary_sequential_e2 : _zz_6_string = "e2  ";
      default : _zz_6_string = "????";
    endcase
  end
  always @(*) begin
    case(_zz_7)
      `StateMachineEnum_binary_sequential_boot : _zz_7_string = "boot";
      `StateMachineEnum_binary_sequential_e1 : _zz_7_string = "e1  ";
      `StateMachineEnum_binary_sequential_e2 : _zz_7_string = "e2  ";
      default : _zz_7_string = "????";
    endcase
  end
  always @(*) begin
    case(_zz_8)
      `StateMachineEnum_1_binary_sequential_boot : _zz_8_string = "boot";
      `StateMachineEnum_1_binary_sequential_e1 : _zz_8_string = "e1  ";
      `StateMachineEnum_1_binary_sequential_e2 : _zz_8_string = "e2  ";
      default : _zz_8_string = "????";
    endcase
  end
  always @(*) begin
    case(_zz_9)
      `StateMachineEnum_1_binary_sequential_boot : _zz_9_string = "boot";
      `StateMachineEnum_1_binary_sequential_e1 : _zz_9_string = "e1  ";
      `StateMachineEnum_1_binary_sequential_e2 : _zz_9_string = "e2  ";
      default : _zz_9_string = "????";
    endcase
  end
  always @(*) begin
    case(fsm_stateReg)
      `fsm_enumDefinition_binary_sequential_boot : fsm_stateReg_string = "boot      ";
      `fsm_enumDefinition_binary_sequential_fsm_stateA : fsm_stateReg_string = "fsm_stateA";
      `fsm_enumDefinition_binary_sequential_fsm_stateB : fsm_stateReg_string = "fsm_stateB";
      `fsm_enumDefinition_binary_sequential_fsm_stateC : fsm_stateReg_string = "fsm_stateC";
      `fsm_enumDefinition_binary_sequential_fsm_stateD : fsm_stateReg_string = "fsm_stateD";
      default : fsm_stateReg_string = "??????????";
    endcase
  end
  always @(*) begin
    case(fsm_stateNext)
      `fsm_enumDefinition_binary_sequential_boot : fsm_stateNext_string = "boot      ";
      `fsm_enumDefinition_binary_sequential_fsm_stateA : fsm_stateNext_string = "fsm_stateA";
      `fsm_enumDefinition_binary_sequential_fsm_stateB : fsm_stateNext_string = "fsm_stateB";
      `fsm_enumDefinition_binary_sequential_fsm_stateC : fsm_stateNext_string = "fsm_stateC";
      `fsm_enumDefinition_binary_sequential_fsm_stateD : fsm_stateNext_string = "fsm_stateD";
      default : fsm_stateNext_string = "??????????";
    endcase
  end
  `endif

  assign fsm_wantExit = 1'b0;
  always @ (*) begin
    _zz_2 = 1'b0;
    case(_zz_8)
      `StateMachineEnum_1_binary_sequential_e1 : begin
      end
      `StateMachineEnum_1_binary_sequential_e2 : begin
        if(_zz_12)begin
          _zz_2 = 1'b1;
        end
      end
      default : begin
      end
    endcase
  end

  always @ (*) begin
    _zz_4 = 1'b0;
    case(_zz_6)
      `StateMachineEnum_binary_sequential_e1 : begin
      end
      `StateMachineEnum_binary_sequential_e2 : begin
        if(_zz_13)begin
          _zz_4 = 1'b1;
        end
      end
      default : begin
      end
    endcase
  end

  always @ (*) begin
    io_result = 1'b0;
    if((_zz_10 && (! _zz_11)))begin
      io_result = 1'b1;
    end
  end

  always @ (*) begin
    io_when_is_next = 1'b0;
    case(fsm_stateNext)
      `fsm_enumDefinition_binary_sequential_fsm_stateA : begin
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateB : begin
        io_when_is_next = 1'b1;
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateC : begin
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateD : begin
      end
      default : begin
      end
    endcase
  end

  always @ (*) begin
    io_int_fsm1_flag = 1'b0;
    case(_zz_8)
      `StateMachineEnum_1_binary_sequential_e1 : begin
      end
      `StateMachineEnum_1_binary_sequential_e2 : begin
        io_int_fsm1_flag = 1'b1;
      end
      default : begin
      end
    endcase
  end

  always @ (*) begin
    io_int_fsm2_flag = 1'b0;
    case(_zz_6)
      `StateMachineEnum_binary_sequential_e1 : begin
      end
      `StateMachineEnum_binary_sequential_e2 : begin
        io_int_fsm2_flag = 1'b1;
      end
      default : begin
      end
    endcase
  end

  always @ (*) begin
    _zz_7 = _zz_6;
    case(_zz_6)
      `StateMachineEnum_binary_sequential_e1 : begin
        _zz_7 = `StateMachineEnum_binary_sequential_e2;
      end
      `StateMachineEnum_binary_sequential_e2 : begin
        if(_zz_13)begin
          _zz_7 = `StateMachineEnum_binary_sequential_boot;
        end
      end
      default : begin
      end
    endcase
    if(_zz_14)begin
      _zz_7 = `StateMachineEnum_binary_sequential_e1;
    end
  end

  always @ (*) begin
    _zz_9 = _zz_8;
    case(_zz_8)
      `StateMachineEnum_1_binary_sequential_e1 : begin
        _zz_9 = `StateMachineEnum_1_binary_sequential_e2;
      end
      `StateMachineEnum_1_binary_sequential_e2 : begin
        if(_zz_12)begin
          _zz_9 = `StateMachineEnum_1_binary_sequential_boot;
        end
      end
      default : begin
      end
    endcase
    if(_zz_14)begin
      _zz_9 = `StateMachineEnum_1_binary_sequential_e1;
    end
  end

  assign _zz_10 = (fsm_stateReg == `fsm_enumDefinition_binary_sequential_fsm_stateB);
  assign _zz_11 = (fsm_stateNext == `fsm_enumDefinition_binary_sequential_fsm_stateB);
  always @ (*) begin
    fsm_stateNext = fsm_stateReg;
    case(fsm_stateReg)
      `fsm_enumDefinition_binary_sequential_fsm_stateA : begin
        fsm_stateNext = `fsm_enumDefinition_binary_sequential_fsm_stateB;
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateB : begin
        if((fsm_counter == 8'h04))begin
          fsm_stateNext = `fsm_enumDefinition_binary_sequential_fsm_stateC;
        end
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateC : begin
        if((_zz_1 <= 4'b0001))begin
          fsm_stateNext = `fsm_enumDefinition_binary_sequential_fsm_stateD;
        end
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateD : begin
        if((((_zz_8 == `StateMachineEnum_1_binary_sequential_boot) || _zz_2) && ((_zz_6 == `StateMachineEnum_binary_sequential_boot) || _zz_4)))begin
          fsm_stateNext = `fsm_enumDefinition_binary_sequential_fsm_stateA;
        end
      end
      default : begin
        fsm_stateNext = `fsm_enumDefinition_binary_sequential_fsm_stateA;
      end
    endcase
  end

  always @ (posedge clk or posedge reset) begin
    if (reset) begin
      fsm_counter <= 8'h0;
      _zz_6 <= `StateMachineEnum_binary_sequential_boot;
      _zz_8 <= `StateMachineEnum_1_binary_sequential_boot;
      fsm_stateReg <= `fsm_enumDefinition_binary_sequential_boot;
    end else begin
      _zz_6 <= _zz_7;
      _zz_8 <= _zz_9;
      fsm_stateReg <= fsm_stateNext;
      case(fsm_stateReg)
        `fsm_enumDefinition_binary_sequential_fsm_stateA : begin
        end
        `fsm_enumDefinition_binary_sequential_fsm_stateB : begin
          fsm_counter <= (fsm_counter + 8'h01);
        end
        `fsm_enumDefinition_binary_sequential_fsm_stateC : begin
        end
        `fsm_enumDefinition_binary_sequential_fsm_stateD : begin
        end
        default : begin
        end
      endcase
      if(((! _zz_10) && _zz_11))begin
        fsm_counter <= 8'h0;
      end
    end
  end

  always @ (posedge clk) begin
    case(_zz_6)
      `StateMachineEnum_binary_sequential_e1 : begin
      end
      `StateMachineEnum_binary_sequential_e2 : begin
        _zz_5 <= (_zz_5 - 3'b001);
      end
      default : begin
      end
    endcase
    if(((! (_zz_6 == `StateMachineEnum_binary_sequential_e2)) && (_zz_7 == `StateMachineEnum_binary_sequential_e2)))begin
      _zz_5 <= 3'b110;
    end
    case(_zz_8)
      `StateMachineEnum_1_binary_sequential_e1 : begin
      end
      `StateMachineEnum_1_binary_sequential_e2 : begin
        _zz_3 <= (_zz_3 - 2'b01);
      end
      default : begin
      end
    endcase
    if(((! (_zz_8 == `StateMachineEnum_1_binary_sequential_e2)) && (_zz_9 == `StateMachineEnum_1_binary_sequential_e2)))begin
      _zz_3 <= 2'b11;
    end
    case(fsm_stateReg)
      `fsm_enumDefinition_binary_sequential_fsm_stateA : begin
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateB : begin
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateC : begin
        _zz_1 <= (_zz_1 - 4'b0001);
      end
      `fsm_enumDefinition_binary_sequential_fsm_stateD : begin
      end
      default : begin
      end
    endcase
    if(((! (fsm_stateReg == `fsm_enumDefinition_binary_sequential_fsm_stateC)) && (fsm_stateNext == `fsm_enumDefinition_binary_sequential_fsm_stateC)))begin
      _zz_1 <= 4'b1010;
    end
  end


endmodule
