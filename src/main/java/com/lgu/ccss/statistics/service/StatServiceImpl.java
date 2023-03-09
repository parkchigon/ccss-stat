package com.lgu.ccss.statistics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lgu.ccss.statistics.constant.StatConst;
import com.lgu.ccss.statistics.mapper.StatMapper;
import com.lgu.ccss.statistics.model.StatVO;
import com.lgu.ccss.statistics.service.worker.StatSetWorker;

@Service
public class StatServiceImpl implements StatService{

	private final Logger logger = LoggerFactory.getLogger(StatServiceImpl.class);
	
	@Value("#{config['statistics.filePath']}")
	private String logFilePath;
	@Value("#{config['statistics.fileDonePath']}")
	private String logFileDonePath;
	@Value("#{config['statistics.fileFailPath']}")
	private String logFileFailPath;
//	@Value("#{config['statistics.suffixName']}")
//	private String suffixName;
	@Value("#{config['statistics.middleName']}")
	private String middleName;
	@Autowired
	private StatMapper statMapper;
	@Autowired
	private StatSetWorker statSetWorker;

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		logger.info("StatisticsService Start!");

		// 통계파일 폴더에서 각 서버리스트 가져온다.
		List<String> tloDateDir = new ArrayList<String>();
		File filePath = new File(logFilePath);
		File[] fileListArr = filePath.listFiles();
		for (File temp : fileListArr) {
			if(temp.isDirectory() && !((temp.getName().indexOf("done") > -1) || (temp.getName().indexOf("fail") > -1))) {
				logger.debug("Dirtory Name : "+temp.getName());
				tloDateDir.add(temp.getName());
			}
		}
		// WAS별 로그폴더 처리
		folderRead(tloDateDir);
	}
	
	private void folderRead(List<String> tloDateDir) {
		// 서버리스트 목록파일에서 폴더안으로 진입하여 로그파일을 가져온다.
		for (String tloDate : tloDateDir) {
			logger.info("tloDate : " + tloDate);

			List<String> fileNameList = new ArrayList<String>();
			String path = logFilePath + StatConst.SLASH + tloDate;
			File filePath = new File(path);
			File[] fileListArr = filePath.listFiles();
//			logger.debug("lenth : " + fileListArr.length);
			if (fileListArr != null) {
				for (File temp : fileListArr) {
//					if (temp.getName().indexOf(suffixName) > -1)
//						fileNameList.add(temp.getName());
						
					if(temp.getName().indexOf(tloDate) > -1) {
						fileNameList.add(temp.getName());
					}
				}
				// 처리대상파일 List
				List<String> fileNameProcessList = targetFileCheck(fileNameList, tloDate);
				
				// 파일처리 진행
				fileRead(fileNameProcessList, tloDate);
				
//				if(fileRead(fileNameProcessList, tloDate)) {
//					if(filePath.listFiles().length == 0) {
////						Date nowDt = new Date();
////						Calendar nowDt = Calendar.getInstance();
////						String nowDtStr = String.valueOf(nowDt.get(Calendar.YEAR))+String.valueOf(nowDt.get(Calendar.MONTH))+String.valueOf(nowDt.get(Calendar.DATE));
//						
//						String nowDtStr = DateUtils.getCurrentTime("yyyyMMdd");
//						logger.debug("[nowDtStr : "+nowDtStr+ " | tloDate  : "+tloDate + " ]");
//						if(tloDate.indexOf(nowDtStr) < 0) {
//							filePath.delete();
//						}	
//					}
//				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	public List<String> targetFileCheck(List<String> fileNameList, String tloDate){
		List<String> returnList = new ArrayList<String>();
		
		// 파일 수정 날짜 기준으로 1분이하 인것을 리스트 에서 제거후 반납
		for(String fileName : fileNameList) {
			String checkFilePath = logFilePath + StatConst.SLASH + tloDate + StatConst.SLASH + fileName;
			File filePath = new File(checkFilePath);
			
			
			
			long lastModifiedDt = filePath.lastModified();
//			
//			int diffMin = DateUtils.diffMin(lastModifiedDt);
//			logger.debug("diffTime(min) : "+diffMin);
//			
//			// 파일수정 시간이 2분 이상인것으로 리스트 재작성
//			if(diffMin > 2) {
//				returnList.add(fileName);
//			}
			
			Date modifyTime = new Date(lastModifiedDt);
			Date nowTime = new Date();
			modifyTime.getMinutes();
			if(modifyTime.getMinutes() != nowTime.getMinutes()) {
				returnList.add(fileName);
			}
		}
		return returnList;
	}
	
//	public File[] getFileList() {
//		logger.debug("GetFileList filePath : "+logFilePath);
//		File filePath = new File(logFilePath);
//		File[] fileListArr = filePath.listFiles();
//		logger.debug("Total File Count : "+fileListArr.length);
//		return fileListArr;
//	}
//	
	public boolean fileRead(List<String> fileNameList, String tloDate) {		
		for(String temp : fileNameList) {
			BufferedReader br = null;
			List<StatVO> statisticsList = new ArrayList<StatVO>();
			String finishPath = logFileFailPath;
			String fileName = temp;
			String openFile = logFilePath + StatConst.SLASH + tloDate+ StatConst.SLASH + fileName;
			
			try {
				
				
				File file = new File(openFile);
				
				if(file.isFile()) {
					logger.info("OPEN FILE("+fileName+"), FILE SIZE(" +file.length()+")");
					FileReader fReader = new FileReader(file);
					br = new BufferedReader(fReader);
					
					String logContent = null;
					
					while ((logContent = br.readLine()) != null) {
						String[] logLineList = logContent.split("\\|");
						
						// logLineContent -> VO
						if(logContent.equals("") || logContent == null) {
							continue;
						}
						logger.debug("logContent : "+logContent);
						StatVO statVo = statSetWorker.statStrToStatVo(logLineList);
						
						// 통계내역 VO로데이터 삽입
						if(statVo == null) {
							continue;
						}
						statisticsList.add(statVo);
						
						// List의 수가 500개 이상일때 500개 단위로 DB Insert!
						if(statisticsList.size() > 500)	{
							insertLogContent(statisticsList);
							statisticsList.clear();
						}
					}
					
					if(statisticsList.size() > 0){
						insertLogContent(statisticsList);
					}
					
					br.close();
					fReader.close();
					
				}
				finishPath = logFileDonePath;
				// 완료되면 처리된 파일 삭제
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("Error ",e);
				finishPath = logFileFailPath;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error ",e);
				finishPath = logFileFailPath;
			}finally {
				moveFile(finishPath,tloDate,openFile,fileName);
				if(fileName.indexOf(middleName) > -1) {
					deleteDir(tloDate);
				}
			}
		}
		return true;
	}
	private void deleteDir(String dirName) {
		try{
			if ( dirName == null) {
				return;
			}
			String dirPath = logFilePath + StatConst.SLASH + dirName;
			File deleteDir = new File(dirPath);
			
			if(deleteDir.isDirectory()) {
				deleteDir.delete();
				logger.debug("SUCCESS Dir Delete(" + deleteDir.getName() + ")");
			} else {
				logger.error("FAIL Delete(" + deleteDir + ") is not Dir");
			}
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	private void moveFile(String finishPath,String tloDate, String pathName, String fileName) {
		try{
			if ( fileName == null)
				return;
			String donePath = finishPath + StatConst.SLASH + tloDate;
			File doneDir = new File(donePath);
			if(!doneDir.exists()) {
				doneDir.mkdirs();
			}
			String moveFilePath = donePath + StatConst.SLASH + fileName;
			File moveFile = new File(pathName);
			logger.debug("[ fileName : "+pathName + " | donePath : "+donePath +" ]");
			if(moveFile.renameTo(new File(moveFilePath))) {
				
				logger.debug("SUCCESS File Move(" + moveFile.getName() + ")");
			}else {
				logger.error("FAIL Move(" + fileName + ")");
			}
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	private void insertLogContent(List<StatVO> statisticsList) {
		// 리스트 DB입력
		
		statMapper.insertStatistics(statisticsList);
	}
}
