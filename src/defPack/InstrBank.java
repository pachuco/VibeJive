/* This class holds strings for patch classes, patches, drums
 * and note names as well as retrieval functions.
*/

package defPack;

public final class InstrBank {

   private static final String[] patchClass = new String[]{"Piano", "Chromatic Percussion", "Organ", "Guitar", "Bass", "Solo Strings", "Ensemble", "Brass", "Reed", "Pipe", "Synth Lead", "Synth Pad", "Synth Effects", "Ethnic", "Percussive", "Sound Effects"};
   private static final String[][] patchName = new String[][]{{"Acoustic Grand Piano", "Bright Acoustic Piano", "Electric Grand Piano", "Honky-tonk Piano", "Electric Piano 1", "Electric Piano 2", "Harpsichord", "Clavi"}, {"Celesta", "Glockenspiel", "Music Box", "Vibraphone", "Marimba", "Xylophone", "Tubular Bells", "Dulcimer"}, {"Drawbar Organ", "Percussive Organ", "Rock Organ", "Church Organ", "Reed Organ", "Accordion", "Harmonica", "Tango Accordion"}, {"Acoustic Guitar (nylon)", "Acoustic Guitar (steel)", "Electric Guitar (jazz)", "Electric Guitar (clean)", "Electric Guitar (muted)", "Overdriven Guitar", "Distortion Guitar", "Guitar harmonics"}, {"Acoustic Bass", "Electric Bass (finger)", "Electric Bass (pick)", "Fretless Bass", "Slap Bass 1", "Slap Bass 2", "Synth Bass 1", "Synth Bass 2"}, {"Violin", "Viola", "Cello", "Contrabass", "Tremolo Strings", "Pizzicato Strings", "Orchestral Harp", "Timpani"}, {"String Ensemble 1", "String Ensemble 2", "SynthStrings 1", "SynthStrings 2", "Choir Aahs", "Voice Oohs", "Synth Voice", "Orchestra Hit"}, {"Trumpet", "Trombone", "Tuba", "Muted Trumpet", "French Horn", "Brass Section", "SynthBrass 1", "SynthBrass 2"}, {"Soprano Sax", "Alto Sax", "Tenor Sax", "Baritone Sax", "Oboe", "English Horn", "Bassoon", "Clarinet"}, {"Piccolo", "Flute", "Recorder", "Pan Flute", "Blown Bottle", "Shakuhachi", "Whistle", "Ocarina"}, {"Lead 1 (square)", "Lead 2 (sawtooth)", "Lead 3 (calliope)", "Lead 4 (chiff)", "Lead 5 (charang)", "Lead 6 (voice)", "Lead 7 (fifths)", "Lead 8 (bass + lead)"}, {"Pad 1 (new age)", "Pad 2 (warm)", "Pad 3 (polysynth)", "Pad 4 (choir)", "Pad 5 (bowed)", "Pad 6 (metallic)", "Pad 7 (halo)", "Pad 8 (sweep)"}, {"FX 1 (rain)", "FX 2 (soundtrack)", "FX 3 (crystal)", "FX 4 (atmosphere)", "FX 5 (brightness)", "FX 6 (goblins)", "FX 7 (echoes)", "FX 8 (sci-fi)"}, {"Sitar", "Banjo", "Shamisen", "Koto", "Kalimba", "Bag pipe", "Fiddle", "Shanai"}, {"Tinkle Bell", "Agogo", "Steel Drums", "Woodblock", "Taiko Drum", "Melodic Tom", "Synth Drum", "Reverse Cymbal"}, {"Guitar Fret Noise", "Breath Noise", "Seashore", "Bird Tweet", "Telephone Ring", "Helicopter", "Applause", "Gunshot"}};
   private static final String[] patchDrm = new String[]{"Acoustic Bass Drum", "Bass Drum 1", "Side Stick", "Acoustic Snare", "Hand Clap", "Electric Snare", "Low Floor Tom", "Closed Hi-hat", "High Floor Tom", "Pedal Hi-hat", "Low Tom", "Open Hi-hat", "Low-mid Tom", "High-mid Tom", "Crash Cymbal 1", "High Tom", "Ride Cymbal 1", "Chinese Cymbal", "Ride Bell", "Tambourine", "Splash Cymbal", "Cowbell", "Crash Cymbal 2", "Vibraslap", "Ride Cymbal 2", "High Bongo", "Low Bongo", "Mute High Conga", "Open High Conga", "Low Conga", "High Timbale", "Low Timbale", "High Agogo", "Low Agogo", "Casaba", "Maracas", "Short Whistle", "Long Whistle", "Short Guiro", "Long Guiro", "Claves", "High Wood Block", "Low Wood Block", "Mute Cuica", "Open Cuica", "Mute Triangle", "Open Triangle"};
   private static final String[] seqDrm = new String[]{"BDA", "BD1", "STK", "SN1", "CLP", "SN2", "LFT", "CHH", "HFT", "PHH", "LTM", "OHH", "LMT", "HMT", "CC1", "HTM", "RC1", "CHC", "RBL", "TAM", "SCY", "COW", "CC2", "VIB", "RC2", "HBO", "LBO", "MHC", "OHC", "LCO", "HTI", "LTI", "HAG", "LAG", "CAS", "MAR", "SWH", "LWH", "SGU", "LGU", "CLA", "HWB", "LWB", "MCU", "OCU", "MTR", "OTR"};
   private static final String[] seqNotes = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};


   public static String a(int var0) {
      return patchName[var0 / 8][var0 % 8];
   }

   public static String[] a() {
      return patchClass;
   }

   public static String[] b(int var0) {
      return patchName[var0];
   }

   public static int b() {
      return 35;
   }

   public static String[] c() {
      return patchDrm;
   }

   public static String funDrmShortName(int var0) {
      return seqDrm[var0 - b()];
   }

   public static String d(int var0) {
      return patchDrm[var0 - b()];
   }

   public static String e(int var0) {
      return seqNotes[var0 % 12];
   }

   public static int f(int var0) {
      return var0 / 12;
   }
}
