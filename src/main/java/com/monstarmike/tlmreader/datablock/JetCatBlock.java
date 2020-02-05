package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;
import com.sun.istack.Nullable;

public final class JetCatBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;														// Source device = 0x15
//		UINT8		sID;															// Secondary ID
//		UINT8		status;															// Table below
//		UINT8		throttle;														// (BCD) xx Percent
//		UINT16		packVoltage;													// (BCD) xx.yy
//		UINT16		pumpVoltage;													// (BCD) xx.yy
//		UINT32		RPM;															// (BCD)
//		UINT16		EGT;															// (BCD) Temperature, Celsius
//		UINT8		offCondition;													// Table below
//		UINT8		spare;
//	} STRU_TELE_JETCAT;

	enum JETCAT_ECU_TURBINE_STATE {							// ECU Status definitions
			JETCAT_ECU_STATE_OFF(0x00),
			JETCAT_ECU_STATE_WAIT_for_RPM(0x01), // (Stby/Start)
			JETCAT_ECU_STATE_Ignite(0x02),
			JETCAT_ECU_STATE_Accelerate(0x03),
			JETCAT_ECU_STATE_Stabilise(0x04),
			JETCAT_ECU_STATE_Learn_HI(0x05),
			JETCAT_ECU_STATE_Learn_LO(0x06),
			JETCAT_ECU_STATE_UNDEFINED(0x07),
			JETCAT_ECU_STATE_Slow_Down(0x08),
			JETCAT_ECU_STATE_Manual(0x09),
			JETCAT_ECU_STATE_AutoOff(0x10),
			JETCAT_ECU_STATE_Run(0x11), // (reg.)
			JETCAT_ECU_STATE_Accleleration_delay(0x12),
			JETCAT_ECU_STATE_SpeedReg(0x13), // (Speed Ctrl)
			JETCAT_ECU_STATE_Two_Shaft_Regulate(0x14), // (only for secondary shaft)
			JETCAT_ECU_STATE_PreHeat1(0x15),
			JETCAT_ECU_STATE_PreHeat2(0x16),
			JETCAT_ECU_STATE_MainFStart(0x17),
			JETCAT_ECU_STATE_NotUsed(0x18),
			JETCAT_ECU_STATE_KeroFullOn(0x19),
			// undefined states 0x1A-0x1F
			EVOJET_ECU_STATE_off(0x20),
			EVOJET_ECU_STATE_ignt(0x21),
			EVOJET_ECU_STATE_acce(0x22),
			EVOJET_ECU_STATE_run(0x23),
			EVOJET_ECU_STATE_cal(0x24),
			EVOJET_ECU_STATE_cool(0x25),
			EVOJET_ECU_STATE_fire(0x26),
			EVOJET_ECU_STATE_glow(0x27),
			EVOJET_ECU_STATE_heat(0x28),
			EVOJET_ECU_STATE_idle(0x29),
			EVOJET_ECU_STATE_lock(0x2A),
			EVOJET_ECU_STATE_rel(0x2B),
			EVOJET_ECU_STATE_spin(0x2C),
			EVOJET_ECU_STATE_stop(0x2D),
			// undefined states 0x2E-0x2F
			HORNET_ECU_STATE_OFF(0x30),
			HORNET_ECU_STATE_SLOWDOWN(0x31),
			HORNET_ECU_STATE_COOL_DOWN(0x32),
			HORNET_ECU_STATE_AUTO(0x33),
			HORNET_ECU_STATE_AUTO_HC(0x34),
			HORNET_ECU_STATE_BURNER_ON(0x35),
			HORNET_ECU_STATE_CAL_IDLE(0x36),
			HORNET_ECU_STATE_CALIBRATE(0x37),
			HORNET_ECU_STATE_DEV_DELAY(0x38),
			HORNET_ECU_STATE_EMERGENCY(0x39),
			HORNET_ECU_STATE_FUEL_HEAT(0x3A),
			HORNET_ECU_STATE_FUEL_IGNITE(0x3B),
			HORNET_ECU_STATE_GO_IDLE(0x3C),
			HORNET_ECU_STATE_PROP_IGNITE(0x3D),
			HORNET_ECU_STATE_RAMP_DELAY(0x3E),
			HORNET_ECU_STATE_RAMP_UP(0x3F),
			HORNET_ECU_STATE_STANDBY(0x40),
			HORNET_ECU_STATE_STEADY(0x41),
			HORNET_ECU_STATE_WAIT_ACC(0x42),
			HORNET_ECU_STATE_ERROR(0x43),
			// undefined states 0x44-0x4F
			XICOY_ECU_STATE_Temp_High(0x50),
			XICOY_ECU_STATE_Trim_Low(0x51),
			XICOY_ECU_STATE_Set_Idle(0x52),
			XICOY_ECU_STATE_Ready(0x53),
			XICOY_ECU_STATE_Ignition(0x54),
			XICOY_ECU_STATE_Fuel_Ramp(0x55),
			XICOY_ECU_STATE_Glow_Test(0x56),
			XICOY_ECU_STATE_Running(0x57),
			XICOY_ECU_STATE_Stop(0x58),
			XICOY_ECU_STATE_Flameout(0x59),
			XICOY_ECU_STATE_Speed_Low(0x5A),
			XICOY_ECU_STATE_Cooling(0x5B),
			XICOY_ECU_STATE_Igniter_Bad(0x5C),
			XICOY_ECU_STATE_Starter_F(0x5D),
			XICOY_ECU_STATE_Weak_Fuel(0x5E),
			XICOY_ECU_STATE_Start_On(0x5F),
			XICOY_ECU_STATE_Pre_Heat(0x60),
			XICOY_ECU_STATE_Battery(0x61),
			XICOY_ECU_STATE_Time_Out(0x62),
			XICOY_ECU_STATE_Overload(0x63),
			XICOY_ECU_STATE_Igniter_Fail(0x64),
			XICOY_ECU_STATE_Burner_On(0x65),
			XICOY_ECU_STATE_Starting(0x66),
			XICOY_ECU_STATE_SwitchOver(0x67),
			XICOY_ECU_STATE_Cal_Pump(0x68),
			XICOY_ECU_STATE_Pump_Limit(0x69),
			XICOY_ECU_STATE_No_Engine(0x6A),
			XICOY_ECU_STATE_Pwr_Boost(0x6B),
			XICOY_ECU_STATE_Run_Idle(0x6C),
			XICOY_ECU_STATE_Run_Max(0x6D),
			// undefined states 0x6e-0x73
			JETCENT_ECU_STATE_STOP(0x74),
			JETCENT_ECU_STATE_GLOW_TEST(0x75),
			JETCENT_ECU_STATE_STARTER_TEST(0x76),
			JETCENT_ECU_STATE_PRIME_FUEL(0x77),
			JETCENT_ECU_STATE_PRIME_BURNER(0x78),
			JETCENT_ECU_STATE_MAN_COOL(0x79),
			JETCENT_ECU_STATE_AUTO_COOL(0x7A),
			JETCENT_ECU_STATE_IGN_HEAT(0x7B),
			JETCENT_ECU_STATE_IGNITION(0x7C),
			JETCENT_ECU_STATE_PREHEAT(0x7D),
			JETCENT_ECU_STATE_SWITCHOVER(0x7E),
			JETCENT_ECU_STATE_TO_IDLE(0x7F),
			JETCENT_ECU_STATE_RUNNING(0x80),
			JETCENT_ECU_STATE_STOP_ERROR(0x81),
			// undefined states 0x82-0x8F
			SWIWIN_ECU_STATE_STOP(0x90),
			SWIWIN_ECU_STATE_READY(0x91),
			SWIWIN_ECU_STATE_IGNITION(0x92),
			SWIWIN_ECU_STATE_PREHEAT(0x93),
			SWIWIN_ECU_STATE_FUEL_RAMP(0x94),
			SWIWIN_ECU_STATE_RUNNING(0x95),
			SWIWIN_ECU_STATE_COOLING(0x96),
			SWIWIN_ECU_STATE_RESTART_SWOVER(0x97),
			SWIWIN_ECU_STATE_NOTUSED(0x98),
			// undefined states 0x99-0x9F

			TURBINE_ECU_MAX_STATE(0x9F);
		
		private byte id;
		public static final JETCAT_ECU_TURBINE_STATE VALUES[]	= values();	// use this to avoid cloning if calling values()
		
		JETCAT_ECU_TURBINE_STATE(int newId) {
			this.id = (byte) newId;
		}
		
		@Nullable
		JETCAT_ECU_TURBINE_STATE fromStateByte(int stateByte) {
			for (JETCAT_ECU_TURBINE_STATE tmpState : VALUES) {
				if (tmpState.id == stateByte)
					return tmpState;
			}
			return null;
		}
		
	};

	enum JETCAT_ECU_OFF_CONDITIONS {					// ECU off conditions. Valid only when the ECUStatus = JETCAT_ECU_STATE_OFF
			JETCAT_ECU_OFF_No_Off_Condition_defined,
			JETCAT_ECU_OFF_Shut_down_via_RC,
			JETCAT_ECU_OFF_Overtemperature,
			JETCAT_ECU_OFF_Ignition_timeout,
			JETCAT_ECU_OFF_Acceleration_time_out,
			JETCAT_ECU_OFF_Acceleration_too_slow,
			JETCAT_ECU_OFF_Over_RPM,
			JETCAT_ECU_OFF_Low_Rpm_Off,
			JETCAT_ECU_OFF_Low_Battery,
			JETCAT_ECU_OFF_Auto_Off,
			JETCAT_ECU_OFF_Low_temperature_Off,
			JETCAT_ECU_OFF_Hi_Temp_Off,
			JETCAT_ECU_OFF_Glow_Plug_defective,
			JETCAT_ECU_OFF_Watch_Dog_Timer,
			JETCAT_ECU_OFF_Fail_Safe_Off,
			JETCAT_ECU_OFF_Manual_Off, // (via GSU)
			JETCAT_ECU_OFF_Power_fail, // (Battery fail)
			JETCAT_ECU_OFF_Temp_Sensor_fail, // (only during startup)
			JETCAT_ECU_OFF_Fuel_fail,
			JETCAT_ECU_OFF_Prop_fail,
			JETCAT_ECU_OFF_2nd_Engine_fail,
			JETCAT_ECU_OFF_2nd_Engine_Diff_Too_High,
			JETCAT_ECU_OFF_2nd_Engine_No_Comm,
			JETCAT_ECU_MAX_OFF_COND,
			// Jet Central
			JETCENT_ECU_OFF_No_Off_Condition_defined,		// ECU off conditions. Valid only when the ECUStatus = JETCENT_ECU_STATE_STOP or JETCENT_ECU_STATE_STOP_ERROR or JETCENT_ECU_STATE_RUNNING
			JETCENT_ECU_OFF_IGNITION_ERROR,
			JETCENT_ECU_OFF_PREHEAT_ERROR,
			JETCENT_ECU_OFF_SWITCHOVER_ERROR,
			JETCENT_ECU_OFF_STARTER_MOTOR_ERROR,
			JETCENT_ECU_OFF_TO_IDLE_ERROR,
			JETCENT_ECU_OFF_ACCELERATION_ERROR,
			JETCENT_ECU_OFF_IGNITER_BAD,
			JETCENT_ECU_OFF_MIN_PUMP_OK,
			JETCENT_ECU_OFF_MAX_PUMP_OK,
			JETCENT_ECU_OFF_LOW_RX_BATTERY,
			JETCENT_ECU_OFF_LOW_ECU_BATTERY,
			JETCENT_ECU_OFF_NO_RX,
			JETCENT_ECU_OFF_TRIM_DOWN,
			JETCENT_ECU_OFF_TRIM_UP,
			JETCENT_ECU_OFF_FAILSAFE,
			JETCENT_ECU_OFF_FULL,
			JETCENT_ECU_OFF_RX_SETUP_ERROR,
			JETCENT_ECU_OFF_TEMP_SENSOR_ERROR,
			JETCENT_ECU_OFF_COM_TURBINE_ERROR,
			JETCENT_ECU_OFF_MAX_TEMP,
			JETCENT_ECU_OFF_MAX_AMPS,
			JETCENT_ECU_OFF_LOW_RPM,
			JETCENT_ECU_OFF_ERROR_RPM_SENSOR,
			JETCENT_ECU_OFF_MAX_PUMP,
			JETCENT_ECU_MAX_OFF_COND;
			
			public static final JETCAT_ECU_OFF_CONDITIONS VALUES[]	= values();	// use this to avoid cloning if calling values()
			
			@Nullable
			JETCAT_ECU_OFF_CONDITIONS fromOrdinal(int ordinal) {
				if (ordinal >= 0 && ordinal < VALUES.length)
					return VALUES[ordinal];

				return null;
			}
	}

	public enum ECUStatus {
		
	}
	
	public enum OffStatus {
		
	}
	
	private byte rawECUStatus;
	private byte throttlePercentage;
	private double packVoltage;
	private double pumpVoltage;
	private int rpm;
	private short egt;
	private byte rawOffCondition;
	
	public byte getRawECUStatus() {
		return this.rawECUStatus;
	}
	
	public byte getThrottlePercentage() {
		return this.throttlePercentage;
	}
	
	public double getPackVoltage() {
		return this.packVoltage;
	}
	
	public double getPumpVoltage() {
		return this.pumpVoltage;
	}
	
	public int getRPM(){
		return this.rpm;
	}
	
	public short getEGT() {
		return this.egt;
	}
	
	public byte getRawOffCondition() {
		return this.rawOffCondition;
	}
	
	public JetCatBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("RawECUStatus JC");
		measurementNames.add("Throttle JC");
		measurementNames.add("PackVoltage JC");
		measurementNames.add("PumpVoltage JC");
		measurementNames.add("RPM JC");
		measurementNames.add("EGT JC");
		measurementNames.add("RawOffCondition JC");

		measurementUnits.add("");
		measurementUnits.add("%");
		measurementUnits.add("V");
		measurementUnits.add("V");
		measurementUnits.add("1/min");
		measurementUnits.add("");
		measurementUnits.add("");

		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
	}

	private void decode(byte[] rawData) {
		rawECUStatus = rawData[6];
		throttlePercentage = rawData[7];
		packVoltage = (rawData[8] * 100D) + (rawData[9] / 100D);
		pumpVoltage = (rawData[10] * 100D) + (rawData[11] / 100D);
		rpm = Ints.fromBytes(rawData[12], rawData[13], rawData[14], rawData[15]);
		egt = Shorts.fromBytes(rawData[16], rawData[17]);
		rawOffCondition = rawData[18];
		
		measurementValues.add((int)getRawECUStatus());
		measurementValues.add((int)getThrottlePercentage());
		measurementValues.add((int)getPackVoltage());
		measurementValues.add((int)getPumpVoltage());
		measurementValues.add((int)getRPM());
		measurementValues.add((int)getEGT());
		measurementValues.add((int)getRawOffCondition());
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof JetCatBlock) {
			JetCatBlock jcat = (JetCatBlock) block;
			return jcat.rawECUStatus == rawECUStatus
					&& jcat.throttlePercentage == throttlePercentage
					&& jcat.packVoltage == packVoltage
					&& jcat.pumpVoltage == pumpVoltage
					&& jcat.rpm == rpm
					&& jcat.egt == egt
					&& jcat.rawOffCondition == rawOffCondition;
		}
		return false;
	}

}
