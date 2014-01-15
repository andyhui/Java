/* 将XML文件存入到sdcard里 */
	public void saveSdcard(String filename, String content) throws IOException {
		try {
			// ---SD Card Storage---
			File sdCard = Environment.getExternalStorageDirectory();
			// File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
			File directory = new File(sdCard.getAbsolutePath());
			directory.mkdirs();
			File file = new File(directory, filename);
			OutputStream fOut = new FileOutputStream(file);
			byte[] b = content.getBytes();
			fOut.write(b);
			fOut.close();

			// ---display file saved message---
			Toast.makeText(getBaseContext(), "File saved successfully!",
					Toast.LENGTH_SHORT).show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}