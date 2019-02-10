package com.biz.bbs.service;

import java.text.*;
import java.time.*;
import java.util.*;

import com.biz.bbs.dao.*;
import com.biz.bbs.vo.*;

/*
 * Dao와 연계해서 CRUD에 대한 구체적인 실행을 실시하는 class
 */
public class BBsMainService {

	/*
	 * member 변수들을 생성하는데
	 */
	/*
	 * dao.selectAll()에서 return한 bbsMainVO들을 담을 List
	 */

	List<BBsMainVO> bbsMainList;
	/*
	 * List : ArrayList, LinkedList를 대표하는 interface이다.
	 */

	/*
	 * 어떤 클래스에 대한 객체를 선언할 때 만약 해당 클래스들을 대표하는 interface가 있으면 interface를 자료형으로 하여 선언을
	 * 한다.
	 */
	BBsMainDao mainDao;
	Scanner scan;

	public BBsMainService() {
		bbsMainList = new ArrayList();
		mainDao = new BBsMainDaoimp();
		scan = new Scanner(System.in);
	}

	public void bbsMenu() {
		while (true) {
			System.out.println("=======================================");

			System.out.println("1.리스트보기 2. 추가 3. 수정 4. 삭제 0.종료");
			System.out.println("===========================================");
			System.out.print("선택 >>");

			String strM = scan.nextLine();
			int intM = Integer.valueOf(strM);
			/*
			 * if (intM == 0) return; if (intM == 1) this.viewBBsList(); if (intM == 2)
			 * this.insertBBs(); // 데이터 추가 if (intM == 3) //수정할 데이터를 확인 this.viewBBsList();
			 * this.updateBBs(); // 데이터 수정 if (intM == 4) //삭제할 데이터를 확인 this.viewBBsList();
			 * this.deleteBBs(); // 데이터 삭제 }
			 */
			if (intM == 0)
				return;
			else if (intM == 2)
				this.insertBBs();
			else
				this.viewBBsList();
			if (intM == 1) this.viewBBsList2();
			if (intM == 3)
				this.updateBBs();
			if (intM == 4)
				this.deleteBBs();
		}
	}

	/*
	 * 키보드에서 작성자, 제목, 내용을 입력받고 현재 커뮤터 날짜를 작성일자로 하여 DB 저장하기
	 */
	private void insertBBs() {
		// TODO 게시판 수정하기

		System.out.print("작성자 >> ");
		String strAuth = scan.nextLine();

		System.out.print("제목 >> ");
		String strSubject = scan.nextLine();

		System.out.println("내용 >> ");
		String strText = scan.nextLine();

		// 작성일자 생성
		// Old 버전
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Date today = new Date();

		String strDate = sf.format(today);

		// New(1.8이상) 버전
		LocalDate ld = LocalDate.now();
		strDate = ld.toString();

		// 변수를 vo에 담기.
		BBsMainVO vo = new BBsMainVO();
		vo.setB_date(strDate);
		vo.setB_auth(strAuth);
		vo.setB_subject(strSubject);
		vo.setB_text(strText);

		// vo를 dao에게 전달
		mainDao.insert(vo);

	}

	private void updateBBs() {
		// TODO 게시판 리스트에서 번호를 선택하려면 수정 시작
		System.out.println("수정할 번호 >>(Enter:취소) ");
		String strId = scan.nextLine();
		if(strId.equals("")) {
			System.out.println("수정이 취소되엇습니다.");
			return;
		}
		long longId = Long.valueOf(strId);
		
		
		
		BBsMainVO vo = mainDao.findById(longId);
		System.out.println("수정:내용입력, 수정취소:Enter");
		System.out.println("----------------------------------");
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.print("수정 : ");
		String strAuth = scan.nextLine();

		System.out.println("제목 : v" + vo.getB_subject());
		System.out.print("수정 : ");
		String strSubject = scan.nextLine();

		System.out.println("내용 : " + vo.getB_text());
		System.out.print("수정 : ");
		String strText = scan.nextLine();

		/*
		 * 만약 내용(작성자, 제목, 내용)을 입력하지 않고
		 *  Enter만 입력했으면 원래 내용을 그대로 유지하도록 한다.
		 */
		if (strAuth.equals("") == false) {
			vo.setB_auth(strAuth);
		}
		if (!strSubject.equals("")) {
			vo.setB_subject(strSubject);
		}
		if (!strText.equals("")) {
			
			vo.setB_text(strText);
		}
		mainDao.update(vo);

	}

	private void deleteBBs() {
		// TODO 게시판 삭제하기
		System.out.println("삭제할 번호(Enter:취소) >> ");
		String strId = scan.nextLine();

		long longId = Long.valueOf(strId);
		
		if(longId == 0) {
			System.out.println("삭제가 취소되엇습니다.");
			return;
		}
		/*
		 * 삭제하기 전에 삭제할 데이터를 다시 확인 시켜주자.
		 */

		// id를 기준으로 데이터 1개 가져오기
		BBsMainVO vo = mainDao.findById(longId);

		System.out.println("====================================");
		System.out.println("삭제할 데이터 확인");
		System.out.println("------------------------------------");
		System.out.println("작성일자 : " + vo.getB_date());
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.println("제목 : " + vo.getB_subject());
		System.out.println("내용 : " + vo.getB_text());
		System.out.println("======================================");
		System.out.println("정말 삭제할까요? (YES)>> ");
		String confirm = scan.nextLine();
		if (confirm.equals("YES")) {
			mainDao.delete(longId);
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("취소되었습니다.");
		}

	}

	public void viewBBsList2() {
		// TODO 게시판 내용보기
		System.out.println("확인할 번호(Enter:취소)");
		String strId = scan.nextLine();
		long longId = Long.valueOf(strId);
		if(longId == 0) {
			System.out.println("취소되었습니다");
			return;
		}
		
		BBsMainVO vo = mainDao.findById(longId);
		System.out.println("========================================================================");
		System.out.println("작성일자 :" + vo.getB_date());
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.println("제목 : " + vo.getB_subject());
		System.out.println("내용 : " + vo.getB_text());
		System.out.println("========================================================================");
	}
	
	/*
	 * 게시판 List를 보는 metod() 선언
	 */
	public void viewBBsList() {
		/*
		 * 현재시각 아직 selectAll()이 구현이 되어 있지 않지만 service 입장에서는 selectAll()이 게시판 전체 리스트를
		 * return 해줄 것이라는 가정을 하고 나머지 코드를 작성할 수 있다.
		 */
		bbsMainList = mainDao.selectAll();

		System.out.println("==========================================================================");
		System.out.println("나의 게시판 v1.1");
		System.out.println("==========================================================================");
		System.out.println("No        날짜         작성자           제목");
		System.out.println("--------------------------------------------------------------------------");
		if (bbsMainList == null) {
			System.out.println("데이터가 없습니다.");
		} else {
			for (BBsMainVO vo : bbsMainList) {
				System.out.print(vo.getB_id() + "\t");
				System.out.print(vo.getB_date() + "\t");
				System.out.print(vo.getB_auth() + "\t");
				System.out.print(vo.getB_subject() + "\n");

			}
		}
	}
}
