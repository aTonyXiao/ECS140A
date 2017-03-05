;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;Tony Xiao Feb.23rd;;;;;;;;;;;;
;;;;;;;;;;;;;;;Part 1;;;;;;;;;;;;;;;;;;
;all-length, that takes a list and counts the number of atoms that occur in a list at all levels.
; (readable:enable-basic-curly)\
; (set-syntax-from-char #\{ #\( )
(defun all-length(lista)
  (setf res 0)
  (cond
    ((null lista) 0)
    ((atom lista) 1)
    (T (setf res (+ (all-length (car lista)) (all-length (cdr lista)))))))

;range that takes a list of numbers (with at least one element) and returns a list of length 2 of the smallest and largest numbers.
(defun range(lista)
  (list (apply 'min lista) (apply 'max lista))) ;http://www.programcreek.com/2010/10/lisp-code-find-minimum-element-in-a-list/

;; edit it 
;before that searches a list and returns a list of all elements in between the first two arguments (inclusive).
(defun before(begin end lista)
  (cond
    ((null lista) nil);empty list
    ((equal begin (car lista)) (cons begin (beforeHelp end (cdr lista))))
    ((before begin end (cdr lista)))))

(defun beforeHelp(end lista)
  (cond
    ((null lista) nil)
    ((equal end (car lista)) (list end))
    ((cons (car lista) (beforeHelp end (cdr lista))))))

;split-if that returns a list into two parts. It takes two arguments: a function (f) and a list. All members for which f is true go into one list, and the rest go into another list.
(defun split-if (func lista)
  (list (remove-if func lista) (remove-if-not func lista)))

;;edit here
;group that takes arguments: a list l and a number n. It returns a new list in which the elements of l are grouped into sublists of length n. The remainder is put in a final sublist.
(defun group(l n)
  (group2 l 'nil n n )
)

(defun group2(oldList newList n countDown)
  (cond
    ((null oldList) newList)
    ((and (equal n countDown) (null newList)) (group2 (cdr oldList) (list (group3 oldList n)) n (- countDown 1)))
    ((equal n countDown) (group2 (cdr oldList) (append newList (list (group3 oldList n))) n (- countDown 1)))
    ((equal countDown 0) (group2 oldList newList n n))
    (t (group2 (cdr oldList) newList n (- countDown 1)))
  )
)

(defun group3(oldList countDown)
  (cond
    ((null oldList) nil)
    ((equal 0 countDown) nil)
    (t  (cons (car oldList) (group3 (cdr oldList) (- countDown 1))))
  )
)


;might here
(defun mostn(f l)
  (setf num (reduce (function max)(mapcar f l)))
  (mostnHelp f l () num))

(defun mostnHelp(f l res num)
  (loop for i in l
    do (if (= num (funcall f i))
      (setf res (append res (list i)))))
  (setf res (append (list res) (list num)))
  res
  )

;;;;;;;;;;;;;;;;;;;;Part2;;;;;;;;;;;;;;;;;;
;match that compares a pattern and an assertion. 
(defun match(assertion1 assertion2)
  (cond
    ; ((and (not (null assertion1)) (not (null assertion2)))) ;1 2 not nill
    ((and (null assertion1) (not (null assertion2))) nil); 1 nill 2 not
    ((and (null assertion2) (not (null assertion1))) nil); 2 nill 1 not
    ((and (null assertion1) (null assertion2)) T) ; 1,2 nil
    ((equal (car assertion1) '?) (matchQues assertion1 assertion2)); move to next one
    ((equal (car assertion1) '!) (matchHelp assertion1 assertion2))
    (T (matchDefault assertion1 assertion2))))

(defun matchHelp (assertion1 assertion2); work for !
  (if (match (cdr assertion1) (cdr assertion2)) T (match assertion1 (cdr assertion2))))

(defun matchQues(assertion1 assertion2)
  (match (cdr assertion1) (cdr assertion2)))

(defun matchDefault(assertion1 assertion2)
  (setf facialEq (equal (car assertion1) (car assertion2)))
  (setf deepEq (match (cdr assertion1) (cdr assertion2)))
  (and facialEq deepEq))


;;;;;;;;;;;;;;;;part3;;;;;;;;;;;;;;
;match-var that extends match. The function match-var takes patterns con- taining pattern variables and matches them against assertions and assigns values to variables.

(defun match-var(pattern assertion &optional N)
  (cond
    ((and (null pattern) (not (null assertion))) nil); 1 nill 2 not
    ((and (null assertion) (not (null pattern))) nil); 2 nill 1 not
    ((and (null pattern) (null assertion)) T) ; 1,2 nil
    ((and (listp (car pattern)) (eq (caar pattern) '?))
      (match-varQues pattern assertion))
    ((match-varExclamDetermin pattern assertion N)
        (match-varExclam pattern assertion N))  
    ((match-varExclamDetermin2 pattern assertion N)
        (match-varExclam2 pattern assertion N))
    (T (match-varDefault pattern assertion N))))


(defun match-varQues(pattern assertion)
  (set (cadar pattern)  (car assertion)) 
  (match-var (cdr pattern) (cdr assertion)))

(defun match-varExclamDetermin2(pattern assertion N)
  (setf res (and (listp (car pattern)) (eq (caar pattern) '!)))res)

(defun match-varExclamDetermin(pattern assertion N)
  (setf deter (and (listp (car pattern)) (eq (caar pattern) '!)
  (match-var (cdr pattern) (cdr assertion))))
  deter)

(defun match-varExclam(pattern assertion N)
  (set (cadar pattern) nil)
  ; (set tmp (car assertion))
  (set (cadar pattern) (append N (list (car assertion))))T)

(defun match-varExclam2(pattern assertion N)
  (set (cadar pattern) nil) 
  (set (cadar pattern)(append (eval (cadar pattern)) N (list (car assertion))))   
  (match-var pattern (cdr assertion) (eval (cadar pattern))))

(defun match-varDefault(pattern assertion N)
  (setf res (and (eq (car pattern) (car assertion)) (match-var (cdr pattern) (cdr assertion))))
  res)  